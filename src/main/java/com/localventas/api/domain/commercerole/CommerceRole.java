package com.localventas.api.domain.commercerole;

import com.localventas.api.domain.commercepermission.CommercePermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerce_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"commerce_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // Ej: "OWNER", "ADMIN", "MANAGER", "CASHIER", "VENDOR"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    private Boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    // Permisos de este rol
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommercePermission> permissions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    // Métodos helper
    public void addPermission(CommercePermission permission) {
        permissions.add(permission);
        permission.setRole(this);
    }

    public void removePermission(CommercePermission permission) {
        permissions.remove(permission);
        permission.setRole(null);
    }
}
