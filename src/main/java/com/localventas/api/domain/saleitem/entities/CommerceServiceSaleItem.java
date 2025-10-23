package com.localventas.api.domain.saleitem.entities;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commerceservice.CommerceService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommerceServiceSaleItem extends SaleItem {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_service_id", nullable = false)
    private CommerceService commerceService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(columnDefinition = "TEXT")
    private String serviceNotes;

    @Override
    public String getItemType() {
        return "COMMERCE_SERVICE";
    }
}
