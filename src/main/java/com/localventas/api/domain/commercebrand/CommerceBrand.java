package com.localventas.api.domain.commercebrand;


import com.localventas.api.domain.brand.Brand;
import com.localventas.api.domain.commerce.Commerce;
import com.localventas.api.domain.productdistributor.ProductDistributor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commerce_brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceBrand {
    @EmbeddedId
    private CommerceBrandId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commerceId")
    @JoinColumn(name = "commerce_id", referencedColumnName = "id")
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("brandId")
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Column(name = "is_owner")
    private Boolean isOwner;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
        this.id.setCommerceId(commerce.getId());
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
        this.id.setBrandId(brand.getId());
    }

    @ManyToMany(mappedBy = "brands")
    private Set<ProductDistributor> distributors = new HashSet<>();

}
