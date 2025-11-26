package com.localventas.api.domain.shared.validation;

import com.localventas.api.app.shared.entities.DomainValidationResult;

public interface DomainValidation<R> {
    DomainValidationResult validate(R request);
}
