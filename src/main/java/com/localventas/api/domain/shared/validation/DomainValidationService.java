package com.localventas.api.domain.shared.validation;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.domain.shared.validation.exceptions.DomainValidationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DomainValidationService<R> {
    private final Set<DomainValidation<R>> validations;

    public DomainValidationService(Set<DomainValidation<R>> validations) {
        this.validations = validations;
    }

    public DomainValidationResult validateCreationRequest(R request) {
        return validations.stream()
                .map(validation -> validation.validate(request))
                .reduce(DomainValidationResult.success(), DomainValidationResult::merge);
    }

    public void validateAndThrow(R request) {
        DomainValidationResult result = validateCreationRequest(request);
        if (!result.isSuccess()) {
            throw new DomainValidationException(result.getErrors());
        }
    }
}
