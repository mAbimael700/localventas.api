package com.localventas.api.infra.http.controller.impl;

import com.localventas.api.app.auth.entities.AuthRequest;
import com.localventas.api.app.auth.entities.AuthResponse;
import com.localventas.api.app.auth.entities.AuthUserResponse;
import com.localventas.api.app.auth.entities.AuthUserPrincipal;
import com.localventas.api.app.auth.security.service.TokenService;
import com.localventas.api.app.auth.service.AuthService;
import com.localventas.api.domain.user.entities.User;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import com.localventas.api.domain.user.entities.UserResponse;
import com.localventas.api.domain.user.service.UserService;
import com.localventas.api.infra.http.controller.AuthController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/sign-in")
    @Override
    public ResponseEntity<?> authenticateUser(AuthRequest request) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        Authentication authenticatedUser = authenticationManager.authenticate(authToken);
        AuthUserPrincipal userPrincipal = (AuthUserPrincipal) authenticatedUser.getPrincipal();
        String token = tokenService.generateToken(userPrincipal.getId(), userPrincipal.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/sign-up")
    @Override
    public ResponseEntity<?> signUpUser(UserCreationRequest request) {
         User user = userService.saveUser(request);
         UserResponse userResponse = new UserResponse(user);
         URI location = URI.create("/users/" + userResponse.id());
         return ResponseEntity.created(location).body(userResponse);
    }

    @GetMapping("/me")
    @Override
    public ResponseEntity<?> getUserInformation(
            @AuthenticationPrincipal AuthUserPrincipal userPrincipal
    ) {
        Long userId = userPrincipal.getId();
        Long expiresAt = userPrincipal.getExpiresAt();
        AuthUserResponse response = authService.getUserInfo(userId, expiresAt);
        return ResponseEntity.ok(response);
    }
}
