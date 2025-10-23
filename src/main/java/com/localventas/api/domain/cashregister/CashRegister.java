package com.localventas.api.domain.cashregister;

import com.localventas.api.domain.commerce.Commerce;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "cash_registers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name; // "Caja 1", "Caja Principal", etc.

    @Column(length = 100)
    private String location; // Ubicación física

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id", nullable = false)
    private Commerce commerce;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
