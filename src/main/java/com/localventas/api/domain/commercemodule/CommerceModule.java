package com.localventas.api.domain.commercemodule;

import com.localventas.api.domain.commercepermission.CommercePermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerce_modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String code; // Ej: "SALES", "INVENTORY", "REPORTS"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_name", length = 50)
    private String iconName;

    @Column(name = "display_order")
    private Integer displayOrder;

    private Boolean active = true;

    // Módulo padre (para crear jerarquía)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_module_id")
    private CommerceModule parentModule;

    @OneToMany(mappedBy = "parentModule", cascade = CascadeType.ALL)
    private List<CommerceModule> subModules = new ArrayList<>();

    // Permisos asociados a este módulo
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<CommercePermission> permissions = new ArrayList<>();

    // Métodos helper
    public boolean isSubModule() {
        return parentModule != null;
    }

    public void addSubModule(CommerceModule subModule) {
        subModules.add(subModule);
        subModule.setParentModule(this);
    }
}
