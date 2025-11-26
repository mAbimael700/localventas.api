package com.localventas.api.app.shared.entities;

public record ValidationError(
        String field,
        String message,
        String code
) {
    public ValidationError(String field, String message) {
        this(field, message, "VALIDATION_ERROR");
    }
}
