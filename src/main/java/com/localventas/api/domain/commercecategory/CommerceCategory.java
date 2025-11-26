package com.localventas.api.domain.commercecategory;

import com.localventas.api.domain.commerce.entities.Commerce;
import com.localventas.api.domain.commercediscount.CommerceDiscount;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerce_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class CommerceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private CommerceCategory parentCommerceCategory;

    @Builder.Default
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<CommerceCategory> subcategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "commerceCategory", cascade = CascadeType.ALL)
    private List<CommerceDiscount> discounts = new ArrayList<>();
}
