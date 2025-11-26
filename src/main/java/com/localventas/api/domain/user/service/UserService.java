package com.localventas.api.domain.user.service;

import com.localventas.api.domain.user.entities.User;
import com.localventas.api.domain.user.entities.UserCreationRequest;

public interface UserService {
    User saveUser(UserCreationRequest request);

    User getUserInfoById(Long id);

    User getByEmail(String email);
}
