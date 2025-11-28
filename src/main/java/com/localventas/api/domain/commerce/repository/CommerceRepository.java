package com.localventas.api.domain.commerce.repository;

import com.localventas.api.domain.commerce.entities.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceRepository extends JpaRepository<Commerce, Long> {
    boolean existByName(String name);
}