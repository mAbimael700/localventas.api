package com.localventas.api.domain.user.validation.impl;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.app.shared.entities.ValidationError;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import com.localventas.api.domain.user.repository.UserRepository;
import com.localventas.api.domain.shared.validation.DomainValidation;
import org.springframework.stereotype.Component;

@Component
public class ExistUserValidation implements DomainValidation<UserCreationRequest> {
    private final UserRepository userRepository;

    public ExistUserValidation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public DomainValidationResult validate(UserCreationRequest creationRequest) {
        boolean existByEmail = userRepository.existByEmail(creationRequest.email());

        if (existByEmail){
            return DomainValidationResult.failure(
                    new ValidationError("email", "Email is already used", "EMAIL_USED")
            );
        }

        return DomainValidationResult.success();
    }
}
