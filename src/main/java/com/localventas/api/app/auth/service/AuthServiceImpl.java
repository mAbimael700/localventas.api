package com.localventas.api.app.auth.service;

import com.localventas.api.app.auth.entities.AuthUserResponse;
import com.localventas.api.app.auth.entities.UserManagedCommercesResponse;
import com.localventas.api.domain.user.entities.User;
import com.localventas.api.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserService userService;

    @Override
    public AuthUserResponse getUserInfo(Long id, Long expiresAt) {
        User userInfo = userService.getUserInfoById(id);

        if (userInfo == null){
            throw new RuntimeException();
        }

        String firstName = userInfo.getPersonalInfo().getFirstName();
        String lastName = userInfo.getPersonalInfo().getLastName();

        return new AuthUserResponse(
                userInfo.getId(),
                userInfo.getEmail(),
                firstName,
                lastName,
                firstName + " " + lastName,
                expiresAt
        );
    }

    @Override
    public UserManagedCommercesResponse getCommerceAdminPanelInfo(Long id) {
        return null;
    }
}
