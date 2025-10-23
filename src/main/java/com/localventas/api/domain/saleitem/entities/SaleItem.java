package com.localventas.api.domain.saleitem.entities;

import com.localventas.api.domain.sale.Sale;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double subtotal;

    private Double discount;

    @Column(nullable = false)
    private Double total;

    private String notes;

    // Método abstracto para identificar el tipo de item
    public abstract String getItemType();
}
