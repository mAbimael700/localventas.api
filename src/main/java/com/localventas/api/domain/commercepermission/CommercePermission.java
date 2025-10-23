package com.localventas.api.domain.commercepermission;

import com.localventas.api.domain.commercemodule.CommerceModule;
import com.localventas.api.domain.commercerole.CommerceRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "commerce_permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "module_id", "permission_type"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommercePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private CommerceRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private CommerceModule module;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", nullable = false)
    private PermissionType permissionType;

    private Boolean granted = true;
}
