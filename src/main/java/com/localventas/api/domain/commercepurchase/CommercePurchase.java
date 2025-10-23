package com.localventas.api.domain.commercepurchase;

import com.localventas.api.domain.commercepurchaseitem.CommercePurchaseItem;
import com.localventas.api.domain.productdistributor.ProductDistributor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerce_purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommercePurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id")
    private ProductDistributor distributor;

    // Relación bidireccional con los items de la compra
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommercePurchaseItem> items = new ArrayList<>();

    // Métodos de ayuda para mantener la sincronización bidireccional
    public void addItem(CommercePurchaseItem item) {
        items.add(item);
        item.setPurchase(this);
    }

    public void removeItem(CommercePurchaseItem item) {
        items.remove(item);
        item.setPurchase(null);
    }

    // Método para calcular el total automáticamente
    public void calculateTotal() {
        this.total = items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }
}
