package com.localventas.api.app.shared.entities;

import java.util.ArrayList;
import java.util.List;

public class DomainValidationResult {
    private final boolean success;
    private final List<ValidationError> errors;

    private DomainValidationResult(boolean success, List<ValidationError> errors) {
        this.success = success;
        this.errors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
    }

    public static DomainValidationResult success() {
        return new DomainValidationResult(true, List.of());
    }

    public static DomainValidationResult failure(ValidationError error) {
        return new DomainValidationResult(false, List.of(error));
    }

    public static DomainValidationResult failure(List<ValidationError> errors) {
        return new DomainValidationResult(false, errors);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return new ArrayList<>(errors);
    }

    public String getErrorMessage() {
        return errors.stream()
                .map(ValidationError::message)
                .reduce((a, b) -> a + "; " + b)
                .orElse("");
    }

    public DomainValidationResult merge(DomainValidationResult other) {
        List<ValidationError> allErrors = new ArrayList<>(this.errors);
        allErrors.addAll(other.errors);
        return new DomainValidationResult(
                this.success && other.success,
                allErrors
        );
    }
}
