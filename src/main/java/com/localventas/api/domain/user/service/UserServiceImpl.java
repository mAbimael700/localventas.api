package com.localventas.api.domain.user.service;

import com.localventas.api.app.shared.entities.DomainValidationResult;
import com.localventas.api.domain.shared.validation.exceptions.DomainValidationException;
import com.localventas.api.domain.person.Person;
import com.localventas.api.domain.systemrole.SystemRole;
import com.localventas.api.domain.systemrole.service.SystemRoleService;
import com.localventas.api.domain.user.entities.User;
import com.localventas.api.domain.user.entities.UserStatus;
import com.localventas.api.domain.user.entities.UserCreationRequest;
import com.localventas.api.domain.user.repository.UserRepository;
import com.localventas.api.domain.shared.validation.DomainValidationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DomainValidationService<UserCreationRequest> validationService;
    private final PasswordEncoder passwordEncoder;
    private final SystemRoleService systemRoleService;

    @Override
    public User saveUser(UserCreationRequest request) {
        DomainValidationResult result = validationService.validateCreationRequest(request);

         if (!result.isSuccess()) {
             throw new DomainValidationException(result.getErrors());
        }

        Set<SystemRole> userSystemRoles = systemRoleService.getInitialUserSystemRoles();

        User newUser = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .personalInfo(
                        Person.builder()
                                .firstName(request.firstName())
                                .lastName(request.lastName())
                                .birthday(request.birthday())
                                .build()
                )
                .emailVerified(false)
                .systemRoles(userSystemRoles)
                .status(UserStatus.PENDING_VERIFICATION)
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User getUserInfoById(Long id) {
        // TODO: Implementar
        return null;
    }

    @Override
    public User getByEmail(String email) {
        // TODO: Implementar
        return null;
    }
}
