package com.localventas.api.infra.http.controller;

import com.localventas.api.app.auth.entities.AuthRequest;
import com.localventas.api.app.auth.entities.AuthUserPrincipal;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<?> authenticateUser(AuthRequest request);
    ResponseEntity<?> signUpUser(UserCreationRequest request);
    ResponseEntity<?> getUserInformation(AuthUserPrincipal userPrincipal);
}
