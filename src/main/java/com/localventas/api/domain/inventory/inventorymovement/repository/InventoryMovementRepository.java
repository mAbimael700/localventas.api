package com.localventas.api.domain.inventory.inventorymovement.repository;

import com.localventas.api.domain.inventory.inventorymovement.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
}
