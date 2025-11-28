package com.localventas.api.domain.commerce.validation.impl;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.app.shared.entities.ValidationError;
import com.localventas.api.domain.commerce.entities.CommerceCreationRequest;
import com.localventas.api.domain.commerce.repository.CommerceRepository;
import com.localventas.api.domain.shared.validation.DomainValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommerceNameValidation implements DomainValidation<CommerceCreationRequest> {
    private final CommerceRepository commerceRepository;

    @Override
    public DomainValidationResult validate(CommerceCreationRequest request) {
        boolean nameAlreadyTaken = commerceRepository.existByName(request.name());

        if (nameAlreadyTaken){
          return DomainValidationResult.success();
        }

        return DomainValidationResult.failure(
                new ValidationError(
                        "name",
                        "Commerce name is already taken",
                        "COMMERCE_NAME_TAKEN"
                        )
        );
    }
}