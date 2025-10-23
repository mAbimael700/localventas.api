package com.localventas.api.domain.paymentattempt;

import com.localventas.api.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment paymentReference;

    @Enumerated(EnumType.STRING)
    private PaymentAttemptStatus status;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "gateway_response", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private String gatewayResponse;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
