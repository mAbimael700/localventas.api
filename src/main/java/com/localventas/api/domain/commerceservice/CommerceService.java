package com.localventas.api.domain.commerceservice;

import com.localventas.api.domain.commercecategory.CommerceCategory;
import com.localventas.api.domain.commerce.Commerce;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "commerce_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CommerceCategory commerceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private Commerce commerce;
}
