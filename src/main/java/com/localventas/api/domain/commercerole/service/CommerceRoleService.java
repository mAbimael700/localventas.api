package com.localventas.api.domain.commercerole.service;

import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercerole.CommerceRoleCode;

import java.util.Optional;
import java.util.Set;

public interface CommerceRoleService {
    /**
     * Obtiene los roles de owner (OWNER y ADMIN)
     * @return Set con los roles de owner
     */
    Set<CommerceRole> getOwnerRoles();

    /**
     * Busca un rol por su código
     * @param code Código del rol
     * @return Optional con el rol si existe
     */
    Optional<CommerceRole> findByCode(CommerceRoleCode code);

    /**
     * Obtiene múltiples roles por sus códigos
     * @param codes Set de códigos de roles
     * @return Set con los roles encontrados
     */
    Set<CommerceRole> findByCodes(Set<CommerceRoleCode> codes);

}
