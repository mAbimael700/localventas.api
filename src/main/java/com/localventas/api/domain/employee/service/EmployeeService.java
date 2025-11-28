package com.localventas.api.domain.employee.service;

import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.user.entities.User;

import java.util.Optional;

public interface EmployeeService {
    /**
     * Crea un empleado básico sin roles específicos
     * @param user Usuario a convertir en empleado
     * @param commerceId ID del comercio
     * @return Employee creado
     */
    Employee createEmployee(User user, Long commerceId);

    /**
     * Crea un empleado como owner de un comercio con roles de OWNER y ADMIN
     * @param user Usuario que será el owner
     * @param commerceId ID del comercio
     * @return Employee creado con roles de owner
     */
    Employee createCommerceOwnerEmployee(User user, Long commerceId);

    /**
     * Busca un empleado por usuario y comercio
     * @param userId ID del usuario
     * @param commerceId ID del comercio
     * @return Optional con el empleado si existe
     */
    Optional<Employee> findByUserAndCommerce(Long userId, Long commerceId);
}
