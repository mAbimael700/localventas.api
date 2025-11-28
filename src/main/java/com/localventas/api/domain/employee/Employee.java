package com.localventas.api.domain.employee;

import com.localventas.api.domain.commerce.entities.Commerce;
import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercepermission.CommercePermissionType;
import com.localventas.api.domain.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "commerce_id"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @Column(name = "termination_date")
    private LocalDateTime terminationDate;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userInfo;

    @ManyToOne
    @JoinColumn(name = "commerce_id", referencedColumnName = "id")
    private Commerce commerce;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<CommerceRole> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = EmployeeStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    // Métodos helper
    public void addRole(CommerceRole role) {
        this.roles.add(role);
    }

    public void removeRole(CommerceRole role) {
        this.roles.remove(role);
    }

    public boolean hasRole(CommerceRole role) {
        return this.roles.contains(role);
    }

    public boolean hasPermission(String moduleName, CommercePermissionType commercePermissionType) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission ->
                        permission.getModule().getName().equals(moduleName) &&
                                permission.getCommercePermissionType() == commercePermissionType
                );
    }

}
