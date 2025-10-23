package com.localventas.api.domain.user;

import com.localventas.api.domain.person.Person;
import com.localventas.api.domain.role.SystemRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person personalInfo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_system_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<SystemRole> systemRoles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (systemRoles.isEmpty()) {
            systemRoles.add(SystemRole.USER); // Rol por defecto
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    // Métodos helper
    public void addSystemRole(SystemRole role) {
        this.systemRoles.add(role);
    }

    public void removeSystemRole(SystemRole role) {
        this.systemRoles.remove(role);
    }

    public boolean hasSystemRole(SystemRole role) {
        return this.systemRoles.contains(role);
    }

    public boolean isSystemAdmin() {
        return this.systemRoles.contains(SystemRole.SYSTEM_ADMIN);
    }
}
