package com.localventas.api.domain.inventory;

import com.localventas.api.domain.commerceproduct.CommerceProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManualSaleReconciliation {
    private CommerceProduct product;
    private Integer quantitySold;
    private String notes;
}
