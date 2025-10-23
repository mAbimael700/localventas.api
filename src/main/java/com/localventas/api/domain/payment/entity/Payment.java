package com.localventas.api.domain.payment.entity;

import com.localventas.api.domain.paymentmethod.PaymentMethod;
import com.localventas.api.domain.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;

    private Double amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private Sale sale;

    /**
     * ID de la intención de pago en la pasarela (checkout/session/intent)
     * - MercadoPago: Preference ID
     * - Stripe: Payment Intent ID / Checkout Session ID
     * - PayPal: Order ID
     * - Square: Payment ID (antes del checkout)
     * - Conekta: Order ID
     */
    @Column(name = "gateway_intent_id")
    private String gatewayIntentId;

    /**
     * ID del pago completado/procesado en la pasarela
     * - MercadoPago: Payment ID
     * - Stripe: Payment Intent ID (confirmado) / Charge ID
     * - PayPal: Capture ID / Payment ID
     * - Square: Payment ID (después del checkout)
     * - Conekta: Charge ID
     */
    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    /**
     * ID de la transacción a nivel bancario/procesador (si está disponible)
     * - Número de autorización bancaria
     * - Transaction ID del procesador
     * - Reference number del banco
     */
    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Column(length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_source", length = 20)
    private PaymentSource source;

    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
}
