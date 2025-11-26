package com.localventas.api.domain.shared.validation.exceptions;

import com.localventas.api.app.shared.entities.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class DomainValidationException extends RuntimeException{
    private final List<ValidationError> errors;

    public DomainValidationException(List<ValidationError> errors) {
        super(buildMessage(errors));
        this.errors = errors;
    }

    public DomainValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    private static String buildMessage(List<ValidationError> errors) {
        return errors.stream()
                .map(e -> String.format("%s: %s", e.field(), e.message()))
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
    }

}
