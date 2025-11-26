package com.localventas.api.domain.sale;

import com.localventas.api.domain.cashregistersession.CashRegisterSession;
import com.localventas.api.domain.commerce.entities.Commerce;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.order.Order;
import com.localventas.api.domain.order.OrderStatus;
import com.localventas.api.domain.payment.entity.Payment;
import com.localventas.api.domain.saleitem.entities.CommerceProductSaleItem;
import com.localventas.api.domain.saleitem.entities.CommerceServiceSaleItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Double subtotal;

    private Double total;

    private Double shippingCost;

    private Double tax;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @ManyToOne
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "vendor_employee_id")
    private Employee vendorEmployee;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommerceProductSaleItem> productItems = new ArrayList<>();

    // Items de servicios
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommerceServiceSaleItem> serviceItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SaleOrigin origin; // MANUAL, ECOMMERCE, CASH_REGISTER

    // Solo si la venta fue por caja
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_register_session_id")
    private CashRegisterSession cashRegisterSession;

    // Una venta puede tener múltiples pedidos (para entregas parciales)
    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public void addProductItem(CommerceProductSaleItem item) {
        productItems.add(item);
        item.setSale(this);
    }

    public void addServiceItem(CommerceServiceSaleItem item) {
        serviceItems.add(item);
        item.setSale(this);
    }

    public boolean hasProducts() {
        return productItems != null && !productItems.isEmpty();
    }

    public boolean hasServices() {
        return serviceItems != null && !serviceItems.isEmpty();
    }

    public int getTotalItemsCount() {
        int productCount = productItems != null ? productItems.size() : 0;
        int serviceCount = serviceItems != null ? serviceItems.size() : 0;
        return productCount + serviceCount;
    }

    public boolean hasOrders() {
        return orders != null && !orders.isEmpty();
    }

    public boolean hasPendingOrders() {
        return orders != null && orders.stream()
                .anyMatch(order -> order.getStatus() != OrderStatus.COMPLETED
                        && order.getStatus() != OrderStatus.CANCELLED);
    }

    public boolean isFromEcommerce() {
        return origin == SaleOrigin.ECOMMERCE;
    }

    public boolean isFromCashRegister() {
        return origin == SaleOrigin.CASH_REGISTER;
    }
}
