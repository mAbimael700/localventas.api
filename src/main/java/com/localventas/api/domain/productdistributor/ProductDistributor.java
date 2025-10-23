package com.localventas.api.domain.productdistributor;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commercebrand.CommerceBrand;
import com.localventas.api.domain.commerceproduct.CommerceProduct;
import com.localventas.api.domain.commercepurchase.CommercePurchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_distributors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDistributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "distributor_products",
            joinColumns = @JoinColumn(name = "distributor_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<CommerceProduct> products = new HashSet<>();

    // Relación muchos a muchos con marcas
    @ManyToMany
    @JoinTable(
            name = "distributor_brands",
            joinColumns = @JoinColumn(name = "distributor_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<CommerceBrand> brands = new HashSet<>();

    @OneToMany(mappedBy = "distributor", fetch = FetchType.LAZY)
    private List<CommercePurchase> purchases = new ArrayList<>();


    public void addProduct(CommerceProduct product) {
        this.products.add(product);
        product.getDistributors().add(this);
    }

    public void removeProduct(CommerceProduct product) {
        this.products.remove(product);
        product.getDistributors().remove(this);
    }

    public void addBrand(CommerceBrand brand) {
        this.brands.add(brand);
        brand.getDistributors().add(this);
    }

    public void removeBrand(CommerceBrand brand) {
        this.brands.remove(brand);
        brand.getDistributors().remove(this);
    }
}
