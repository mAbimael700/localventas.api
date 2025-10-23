package com.localventas.api.domain.employee;

import com.localventas.api.domain.commerce.Commerce;
import com.localventas.api.domain.commercerole.CommerceRole;
import com.localventas.api.domain.commercepermission.PermissionType;
import com.localventas.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "commerce_id"}))
@Getter
@Setter
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
    private List<CommerceRole> roles = new ArrayList<>();

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
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(CommerceRole role) {
        this.roles.remove(role);
    }

    public boolean hasRole(CommerceRole role) {
        return this.roles.contains(role);
    }

    public boolean hasPermission(String moduleName, PermissionType permissionType) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission ->
                        permission.getModule().getName().equals(moduleName) &&
                                permission.getPermissionType() == permissionType
                );
    }

}
