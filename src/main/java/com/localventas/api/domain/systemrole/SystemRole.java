package com.localventas.api.domain.systemrole;

import com.localventas.api.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "system_roles")
@Getter
@Setter
public class SystemRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "systemRoles")
    private Set<User> users = new HashSet<>();
}
