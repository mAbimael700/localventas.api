package com.localventas.api.app.auth.entities;

import com.localventas.api.domain.person.Person;
import com.localventas.api.domain.user.entities.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public class AuthUserPrincipal implements UserDetails {
    @Getter
    private final Long id;
    @Getter
    private final String email;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String fullName;
    @Getter
    private final Long expiresAt;

    private final String password;
    private final Set<String> userModules;
    private final Set<String> userRoles;

    public AuthUserPrincipal(User user,
                             Long expiresAt,
                             Set<String> userModules,
                             Set<String> userRoles) {
        Person personalInfo = user.getPersonalInfo();

        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = personalInfo.getFirstName();
        this.lastName = personalInfo.getLastName();
        this.fullName = personalInfo.getFirstName()+ " " + personalInfo.getLastName();
        this.expiresAt = expiresAt;
        this.userModules = userModules;
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
