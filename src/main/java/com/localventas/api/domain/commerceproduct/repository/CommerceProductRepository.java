package com.localventas.api.domain.commerceproduct.repository;

import com.localventas.api.domain.commerceproduct.CommerceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceProductRepository extends JpaRepository<CommerceProduct, Long> {
}