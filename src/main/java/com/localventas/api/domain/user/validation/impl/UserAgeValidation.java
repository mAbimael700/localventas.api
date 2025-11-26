package com.localventas.api.domain.user.validation.impl;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.app.shared.entities.ValidationError;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import com.localventas.api.domain.user.repository.UserRepository;
import com.localventas.api.domain.shared.validation.DomainValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserAgeValidation implements DomainValidation<UserCreationRequest> {
    private UserRepository userRepository;

    @Override
    public DomainValidationResult validate(UserCreationRequest creationRequest) {
        LocalDate today = LocalDate.now();
        boolean isAgeValid = !creationRequest.birthday().plusYears(18).isAfter(today);

        if (isAgeValid){
            return DomainValidationResult.failure(
                    new ValidationError(
                            "dateBirth",
                            "Need be older than 18 years old",
                            "AGE_INVALID"
                    ));
        }

        return DomainValidationResult.success();
    }
}
