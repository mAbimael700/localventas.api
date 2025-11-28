package com.localventas.api.domain.employee.repository;

import com.localventas.api.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUserInfoIdAndCommerceId(Long userId, Long commerceId);
}
