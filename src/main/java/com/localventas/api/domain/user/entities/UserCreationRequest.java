package com.localventas.api.domain.user.entities;

import java.time.LocalDate;

public record UserCreationRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate birthday,
        String phoneNumber
) {
}
