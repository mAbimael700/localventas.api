package com.localventas.api.domain.commercerole.repository;

import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercerole.CommerceRoleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CommerceRoleRepository extends JpaRepository<CommerceRole, Long> {
    @Query("SELECT cr FROM CommerceRole cr WHERE cr.code IN :codes")
    Set<CommerceRole> findByCodeIn(@Param("codes") Set<String> codes);

    Optional<CommerceRole> findByCode(String name);
}
