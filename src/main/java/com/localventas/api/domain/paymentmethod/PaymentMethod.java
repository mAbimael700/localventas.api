package com.localventas.api.domain.paymentmethod;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String description;

    /**
     * Pasarela de pago que maneja este método
     * Ejemplo: "mercadopago", "stripe", "paypal", "square", "conekta"
     */
    @Column(name = "gateway")
    private String gateway;

    /**
     * Configuración adicional en formato JSON
     * Puede incluir configuraciones específicas de la pasarela
     */
    @Column(name = "gateway_configuration", columnDefinition = "json")
    private String gatewayConfiguration;

    /**
     * Tipo específico del método en la pasarela
     * - MercadoPago: "credit_card", "debit_card", "digital_wallet", "bank_transfer"
     * - Stripe: "card", "sepa_debit", "ideal", "sofort"
     * - PayPal: "paypal", "venmo", "paylater"
     */
    @Column(name = "gateway_method_type")
    private String gatewayMethodType;

    @Column(name = "is_active")
    private Boolean isActive = true;

}
