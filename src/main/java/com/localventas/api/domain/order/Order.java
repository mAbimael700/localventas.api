package com.localventas.api.domain.order;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commerce.Commerce;
import com.localventas.api.domain.customer.Customer;
import com.localventas.api.domain.ordersaleitem.OrderSaleItem;
import com.localventas.api.domain.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id", nullable = false)
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; // Cliente del e-commerce

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Relación con la venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    private String customerNotes;
    private String customerName;
    private String customerPhone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderSaleItem> orderSaleItems = new ArrayList<>();

    public void addOrderSaleItem(OrderSaleItem item) {
        orderSaleItems.add(item);
        item.setOrder(this);
    }

    public boolean isEcommerce() {
        return type == OrderType.ECOMMERCE;
    }

    public boolean isLocal() {
        return type == OrderType.LOCAL;
    }

    public boolean isPending() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED || status == OrderStatus.DELIVERED;
    }

    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    public int getTotalItems() {
        return orderSaleItems != null ? orderSaleItems.size() : 0;
    }
}
