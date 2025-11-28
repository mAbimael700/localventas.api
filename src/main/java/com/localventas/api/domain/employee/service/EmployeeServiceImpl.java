package com.localventas.api.domain.employee.service;

import com.localventas.api.domain.commerce.entities.Commerce;
import com.localventas.api.domain.commerce.repository.CommerceRepository;
import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercerole.service.CommerceRoleService;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.employee.EmployeeStatus;
import com.localventas.api.domain.employee.repository.EmployeeRepository;
import com.localventas.api.domain.user.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private CommerceRepository commerceRepository;
    private CommerceRoleService commerceRoleService;

    @Override
    public Employee createEmployee(User user, Long commerceId) {
        Commerce commerce = this.commerceRepository.findById(commerceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Commerce con ID " + commerceId + " no existe"
                ));

        Employee newEmployee = Employee.builder()
                .userInfo(user)
                .hireDate(LocalDateTime.now())
                .commerce(commerce)
                .status(EmployeeStatus.ACTIVE)
                .roles(new HashSet<>())
                .build();

        return this.employeeRepository.save(newEmployee);
    }

    @Override
    public Employee createCommerceOwnerEmployee(User user, Long commerceId) {
        Optional<Employee> existingEmployee = this.findByUserAndCommerce(user.getId(), commerceId);
        if (existingEmployee.isPresent()) {
            throw new IllegalStateException(
                    "El usuario ya es empleado de este comercio"
            );
        }

        Employee ownerEmployee = this.createEmployee(user, commerceId);
        ownerEmployee.setNotes("Commerce owner");

        Set<CommerceRole> ownerRoles = this.commerceRoleService.getOwnerRoles();
        if (ownerRoles.isEmpty()) {
            throw new IllegalStateException(
                    "No se encontraron roles de owner en el sistema"
            );
        }

        ownerEmployee.setRoles(ownerRoles);

        return this.employeeRepository.save(ownerEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findByUserAndCommerce(Long userId, Long commerceId) {
        return employeeRepository.findByUserInfoIdAndCommerceId(userId, commerceId);
    }
}