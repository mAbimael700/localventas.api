package com.localventas.api.app.auth.service;

import com.localventas.api.app.auth.entities.AuthUserResponse;
import com.localventas.api.app.auth.entities.UserManagedCommercesResponse;

public interface AuthService {
    AuthUserResponse getUserInfo(Long id, Long expiresAt);
    UserManagedCommercesResponse getCommerceAdminPanelInfo(Long id);
}
