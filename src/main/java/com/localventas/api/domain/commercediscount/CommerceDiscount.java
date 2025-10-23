package com.localventas.api.domain.commercediscount;

import com.localventas.api.domain.commercebrand.CommerceBrand;
import com.localventas.api.domain.commercecategory.CommerceCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "commerce_discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_brand_id")
    private CommerceBrand commerceBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_category_id")
    private CommerceCategory commerceCategory;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private Integer percentage;
}