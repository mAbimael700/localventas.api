package com.localventas.api.domain.commerce.service;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commerce.entities.*;
import com.localventas.api.domain.commerce.repository.CommerceRepository;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.employee.service.EmployeeService;
import com.localventas.api.domain.user.entities.User;
import com.localventas.api.domain.user.repository.UserRepository;
import com.localventas.api.domain.shared.validation.DomainValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommerceServiceImpl implements CommerceService {

    private final CommerceRepository commerceRepository;
    private final UserRepository userRepository;
    private final DomainValidationService<CommerceCreationRequest> commerceCreationValidationService;
    private final EmployeeService employeeService;

    @Override
    public Optional<Commerce> getCommerceInfo(Long commerceId) {
        return commerceRepository.findById(commerceId);
    }

    @Override
    @Transactional
    public Commerce createCommerce(CommerceCreationRequest request, Long userOwnerId) {
        User user = userRepository.findById(userOwnerId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        this.commerceCreationValidationService.validateAndThrow(request);

        Commerce newCommerce = Commerce.builder()
                .name(request.name())
                .description(request.description())
                .owner(user)
                .employees(new ArrayList<>())
                .build();

        Commerce savedCommerce = this.commerceRepository.save(newCommerce);

        Employee ownerEmployee = employeeService
                .createCommerceOwnerEmployee(user, savedCommerce.getId());

        savedCommerce.addEmployee(ownerEmployee);

        return savedCommerce;
    }

    @Override
    public Optional<Commerce> updateCommerce(Long commerceId, CommerceUpdateRequest request) {
        // TODO: Implementar
        return Optional.empty();
    }

    @Override
    public Set<Address> getCommerceAddress(Long commerceId) {
        // TODO: Implementar
        return Set.of();
    }

    @Override
    public boolean addCommerceAddress(Long commerceId, CommerceAddressCreationRequest addressRequest) {
        // TODO: Implementar
        return false;
    }

    @Override
    public boolean updateCommerceAddress(Long addressId, CommerceAddressUpdateRequest addressUpdateRequest) {
        // TODO: Implementar
        return false;
    }

    @Override
    public boolean removeCommerceAddress(Long commerceId, Long addressId) {
        // TODO: Implementar
        return false;
    }

    @Override
    public List<Commerce> getCommercesByOwner(Long userId) {
        // TODO: Implementar
        return List.of();
    }
}
