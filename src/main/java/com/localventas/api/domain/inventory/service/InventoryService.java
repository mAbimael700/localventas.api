package com.localventas.api.domain.inventory.service;

import com.localventas.api.domain.commerceproduct.CommerceProduct;
import com.localventas.api.domain.commerceproduct.repository.CommerceProductRepository;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.inventory.ManualSaleReconciliation;
import com.localventas.api.domain.inventory.exceptions.InsufficientStockException;
import com.localventas.api.domain.inventory.exceptions.ProductNotFoundException;
import com.localventas.api.domain.inventory.inventorymovement.InventoryMovement;
import com.localventas.api.domain.inventory.inventorymovement.repository.InventoryMovementRepository;
import com.localventas.api.domain.inventory.inventorymovementtype.InventoryMovementType;
import com.localventas.api.domain.sale.Sale;
import com.localventas.api.domain.sale.SaleOrigin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMovementRepository inventoryMovementRepository;
    private final CommerceProductRepository productRepository;

    /**
     * Registra un movimiento de inventario genérico
     */
    @Transactional
    public InventoryMovement registerMovement(
            CommerceProduct product,
            InventoryMovementType type,
            Integer quantity,
            Double unitCost,
            String reason,
            String reference,
            Employee employee
    ) {
        return registerMovementInternal(product, type, quantity, unitCost,
                reason, reference, employee, null);
    }

    /**
     * Registra movimiento por venta FORMAL (con validación de stock)
     */
    @Transactional
    public InventoryMovement registerSaleMovement(
            CommerceProduct product,
            Sale sale,
            Integer quantity,
            Employee employee
    ) {
        // Solo ventas formales afectan inventario
        if (sale.getOrigin() == SaleOrigin.MANUAL) {
            throw new IllegalArgumentException(
                    "Las ventas MANUAL no generan movimientos automáticos de inventario"
            );
        }

        return registerMovementInternal(
                product,
                InventoryMovementType.FORMAL_SALE,
                quantity,
                product.getCost(),
                "Venta formal #" + sale.getOrderNumber(),
                sale.getOrderNumber(),
                employee,
                sale
        );
    }

    /**
     * Reconciliación de ventas manuales al final del día
     */
    @Transactional
    public List<InventoryMovement> reconcileManualSales(
            List<ManualSaleReconciliation> reconciliations,
            Employee employee,
            String notes
    ) {
        return reconciliations.stream()
                .map(rec -> registerMovementInternal(
                        rec.getProduct(),
                        InventoryMovementType.MANUAL_SALE_RECONCILIATION,
                        rec.getQuantitySold(),
                        rec.getProduct().getCost(),
                        "Reconciliación ventas manuales: " + notes,
                        "MANUAL-" + LocalDateTime.now().toLocalDate(),
                        employee,
                        null
                ))
                .toList();
    }

    /**
     * Método interno que realiza el registro
     */
    private InventoryMovement registerMovementInternal(
            CommerceProduct product,
            InventoryMovementType type,
            Integer quantity,
            Double unitCost,
            String reason,
            String reference,
            Employee employee,
            Sale sale
    ) {
        // Validar stock para salidas
        if (type.isDecrease() && product.getExistence() < quantity) {
            throw new InsufficientStockException(
                    String.format("Stock insuficiente para %s. Disponible: %d, Requerido: %d",
                            product.getName(), product.getExistence(), quantity)
            );
        }

        Integer previousStock = product.getExistence();
        Integer newStock = type.isIncrease()
                ? previousStock + quantity
                : previousStock - quantity;

        // Crear movimiento
        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .commerce(product.getCommerce())
                .type(type)
                .quantity(quantity)
                .previousStock(previousStock)
                .newStock(newStock)
                .unitCost(unitCost)
                .reason(reason)
                .reference(reference)
                .employee(employee)
                .sale(sale)
                .build();

        // Actualizar stock del producto
        product.setExistence(newStock);
        product.setModifiedAt(LocalDateTime.now());

        // Actualizar costo en compras
        if (type == InventoryMovementType.PURCHASE && unitCost != null) {
            updateProductCost(product, unitCost);
        }

        productRepository.save(product);
        return inventoryMovementRepository.save(movement);
    }

    private void updateProductCost(CommerceProduct product, Double newCost) {
        product.setCost(newCost);
        if (product.getPrice() != null) {
            product.setRevenue(product.getPrice() - newCost);
        }
    }

    /**
     * Obtener stock actual con validación
     */
    @Transactional(readOnly = true)
    public Integer getCurrentStock(Long productId) {
        CommerceProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return product.getExistence();
    }

    /**
     * Verificar disponibilidad antes de venta
     */
    @Transactional(readOnly = true)
    public boolean hasAvailableStock(Long productId, Integer requiredQuantity) {
        return getCurrentStock(productId) >= requiredQuantity;
    }
}
