package com.localventas.api.domain.commerceproduct;

import com.localventas.api.domain.brand.Brand;
import com.localventas.api.domain.commercecategory.CommerceCategory;
import com.localventas.api.domain.commerce.Commerce;
import com.localventas.api.domain.commerceitem.CommerceItemStatus;
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
@Table(name = "commerce_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    private String description;

    private Double price;

    private Double cost;

    private Double revenue;

    private Integer existence;

    @Enumerated(EnumType.STRING)
    private CommerceItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CommerceCategory commerceCategory;

    @ManyToMany(mappedBy = "products")
    private Set<ProductDistributor> distributors = new HashSet<>();
}
