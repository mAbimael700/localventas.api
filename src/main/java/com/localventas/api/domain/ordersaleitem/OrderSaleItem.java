package com.localventas.api.domain.ordersaleitem;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.order.Order;
import com.localventas.api.domain.saleitem.entities.CommerceProductSaleItem;
import com.localventas.api.domain.saleitem.entities.CommerceServiceSaleItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "order_sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Referencia al item de producto de la venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sale_item_id")
    private CommerceProductSaleItem productSaleItem;

    // Referencia al item de servicio de la venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_sale_item_id")
    private CommerceServiceSaleItem serviceSaleItem;

    // Cantidad de este item que se incluye en ESTE pedido
    // (útil para entregas parciales)
    @Column(nullable = false)
    private Integer quantity;

    // Dirección específica para este item (puede override la del Order)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    // Validación
    @PrePersist
    @PreUpdate
    private void validate() {
        if ((productSaleItem == null && serviceSaleItem == null) ||
                (productSaleItem != null && serviceSaleItem != null)) {
            throw new IllegalStateException(
                    "OrderSaleItem debe tener exactamente un producto O un servicio, no ambos ni ninguno"
            );
        }

        // Validar que la cantidad no exceda la cantidad original del item de venta
        if (productSaleItem != null && quantity > productSaleItem.getQuantity()) {
            throw new IllegalStateException(
                    "La cantidad del pedido no puede exceder la cantidad de la venta"
            );
        }
        if (serviceSaleItem != null && quantity > serviceSaleItem.getQuantity()) {
            throw new IllegalStateException(
                    "La cantidad del pedido no puede exceder la cantidad de la venta"
            );
        }
    }

    // Métodos helper
    public String getItemType() {
        return productSaleItem != null ? "PRODUCT" : "SERVICE";
    }

    public Double getUnitPrice() {
        return productSaleItem != null
                ? productSaleItem.getUnitPrice()
                : serviceSaleItem.getUnitPrice();
    }

    public String getItemName() {
        if (productSaleItem != null) {
            return productSaleItem.getCommerceProduct().getName();
        } else if (serviceSaleItem != null) {
            return serviceSaleItem.getCommerceService().getName();
        }
        return null;
    }
}
