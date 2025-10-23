package com.localventas.api.domain.saleitem.entities;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commerceproduct.CommerceProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceProductSaleItem extends SaleItem {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private CommerceProduct commerceProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Override
    public String getItemType() {
        return "COMMERCE_PRODUCT";
    }
}
