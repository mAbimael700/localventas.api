package com.localventas.api.domain.commerce;

import com.localventas.api.domain.commercecategory.CommerceCategory;
import com.localventas.api.domain.commercebrand.CommerceBrand;
import com.localventas.api.domain.commerceservice.CommerceService;
import com.localventas.api.domain.commerceproduct.CommerceProduct;
import com.localventas.api.domain.employee.Employee;
import com.localventas.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerces")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commerce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @Builder.Default
    @OneToMany(mappedBy = "commerce", fetch = FetchType.LAZY)
    private List<CommerceProduct> commerceProducts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "commerce", fetch = FetchType.LAZY)
    private List<CommerceService> commerceServices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommerceBrand> commerceBrands = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommerceCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    // Métodos helper
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setCommerce(this);
    }

    public boolean isOwner(User user) {
        return this.owner.getId().equals(user.getId());
    }
}
