package com.localventas.api.app.auth.entities;

public record AuthUserResponse(
        Long id,
        String email,
        String name,
        String lastName,
        String fullName,
        Long expires
) {
}
