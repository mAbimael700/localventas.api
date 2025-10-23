package com.localventas.api.domain.commercebrand;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommerceBrandId implements Serializable {

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "commerce_id")
    private Long commerceId;
}
