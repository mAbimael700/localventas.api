package com.localventas.api.domain.user.entities;

public record UserResponse(
        Long id
) {
    public UserResponse(User user) {
        this(user.getId());
    }
}
