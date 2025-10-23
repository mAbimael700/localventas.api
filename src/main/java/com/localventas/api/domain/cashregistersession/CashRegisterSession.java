package com.localventas.api.domain.cashregistersession;

import com.localventas.api.domain.cashregister.CashRegister;
import com.localventas.api.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cash_register_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Empleado responsable

    @Column(name = "opened_at", nullable = false)
    private LocalDateTime openedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "opening_balance", nullable = false)
    private Double openingBalance; // Fondo inicial

    @Column(name = "expected_closing_balance")
    private Double expectedClosingBalance; // Calculado del sistema

    @Column(name = "actual_closing_balance")
    private Double actualClosingBalance; // Conteo físico

    @Column(name = "difference")
    private Double difference; // Diferencia (faltante/sobrante)

    @Enumerated(EnumType.STRING)
    private SessionStatus status; // OPEN, CLOSED

    @Column(columnDefinition = "TEXT")
    private String notes; // Observaciones del cierre
}
