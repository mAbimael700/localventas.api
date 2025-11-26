package com.localventas.api.domain.user.repository;

import com.localventas.api.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existByEmail(String email);
}
