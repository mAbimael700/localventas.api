package com.localventas.api.domain.commercepurchaseitem;

import com.localventas.api.domain.commerceproduct.CommerceProduct;
import com.localventas.api.domain.commercepurchase.CommercePurchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "commerce_purchase_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommercePurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private CommercePurchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private CommerceProduct product;
}
