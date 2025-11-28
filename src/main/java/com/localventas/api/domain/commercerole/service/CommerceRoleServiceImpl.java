package com.localventas.api.domain.commercerole.service;

import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercerole.CommerceRoleCode;
import com.localventas.api.domain.commercerole.repository.CommerceRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommerceRoleServiceImpl implements CommerceRoleService {

    private CommerceRoleRepository commerceRoleRepository;

    @Override
    public Set<CommerceRole> getOwnerRoles() {
        return this.commerceRoleRepository.findByCodeIn(
                Set.of(
                        CommerceRoleCode.OWNER.name(),
                        CommerceRoleCode.ADMIN.name()
                )
        );
    }

    @Override
    public Optional<CommerceRole> findByCode(CommerceRoleCode code) {
        return commerceRoleRepository.findByCode(code.name());
    }

    @Override
    public Set<CommerceRole> findByCodes(Set<CommerceRoleCode> codes) {
        Set<String> codeStrings = codes.stream()
                .map(Enum::name)
                .collect(java.util.stream.Collectors.toSet());

        return commerceRoleRepository.findByCodeIn(codeStrings);
    }
}
