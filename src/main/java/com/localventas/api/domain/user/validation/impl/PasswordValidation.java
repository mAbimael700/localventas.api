package com.localventas.api.domain.user.validation.impl;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.app.shared.entities.ValidationError;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import com.localventas.api.domain.shared.validation.DomainValidation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordValidation implements DomainValidation<UserCreationRequest> {
    private static final int MIN_LENGTH = 8;

    @Override
    public DomainValidationResult validate(UserCreationRequest creationRequest) {
        List<ValidationError> errors = new ArrayList<>();

        if (creationRequest.password() == null || creationRequest.password().isBlank()) {
            errors.add(new ValidationError("password", "La contraseña es requerida", "PASSWORD_REQUIRED"));
            return DomainValidationResult.failure(errors);
        }

        if (creationRequest.password().length() < MIN_LENGTH) {
            errors.add(new ValidationError(
                    "password",
                    String.format("La contraseña debe tener al menos %d caracteres", MIN_LENGTH),
                    "PASSWORD_TOO_SHORT"
            ));
        }

        if (!creationRequest.password().matches(".*[A-Z].*")) {
            errors.add(new ValidationError(
                    "password",
                    "La contraseña debe contener al menos una letra mayúscula",
                    "PASSWORD_NO_UPPERCASE"
            ));
        }

        if (!creationRequest.password().matches(".*[0-9].*")) {
            errors.add(new ValidationError(
                    "password",
                    "La contraseña debe contener al menos un número",
                    "PASSWORD_NO_NUMBER"
            ));
        }

        return errors.isEmpty()
                ? DomainValidationResult.success()
                : DomainValidationResult.failure(errors);
    }
}
