package com.localventas.api.domain.systemrole.service;

import com.localventas.api.domain.systemrole.SystemRole;

import java.util.Set;

public interface SystemRoleService {
    Set<SystemRole> getInitialUserSystemRoles();
}
