package com.localventas.api.domain.inventory.inventorymovement;

import com.localventas.api.domain.commerce.entities.Commerce;
import com.localventas.api.domain.commerceproduct.CommerceProduct;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.inventory.inventorymovementtype.InventoryMovementType;
import com.localventas.api.domain.sale.Sale;
import com.localventas.api.domain.sale.SaleOrigin;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements", indexes = {
        @Index(name = "idx_product_date", columnList = "product_id, created_at"),
        @Index(name = "idx_commerce_date", columnList = "commerce_id, created_at")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private CommerceProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id", nullable = false)
    private Commerce commerce;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryMovementType type;

    @Column(nullable = false)
    private Integer quantity;

    // Snapshot del stock antes y después
    @Column(name = "previous_stock", nullable = false)
    private Integer previousStock;

    @Column(name = "new_stock", nullable = false)
    private Integer newStock;

    // Costos para valorización de inventario
    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "total_cost")
    private Double totalCost;

    // Trazabilidad
    private String reason;

    private String reference; // Número de factura, pedido, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee; // Quién hizo el movimiento

    // NUEVO: Relación con venta (solo para ventas formales)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.totalCost == null && this.unitCost != null) {
            this.totalCost = this.unitCost * this.quantity;
        }
    }

    // Método helper
    public boolean isFromFormalSale() {
        return this.sale != null &&
                (this.sale.getOrigin() == SaleOrigin.ECOMMERCE ||
                        this.sale.getOrigin() == SaleOrigin.CASH_REGISTER);
    }

}
