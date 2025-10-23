package com.localventas.api.domain.role;

public enum SystemRole {
    // Roles de usuario común (fuera del contexto de comercio)
    USER,                    // Usuario registrado básico
    PREMIUM_USER,           // Usuario con beneficios premium

    // Roles administrativos del sistema
    SYSTEM_ADMIN,           // Administrador de la plataforma
    SYSTEM_MODERATOR        // Moderador de la plataforma
}
