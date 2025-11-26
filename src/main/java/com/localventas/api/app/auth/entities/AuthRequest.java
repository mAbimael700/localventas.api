package com.localventas.api.app.auth.entities;

public record AuthRequest(
        String email,
        String password
) {
}
