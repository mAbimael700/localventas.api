package com.localventas.api.domain.payment.entity;

public enum PaymentSource {
    MANUAL,      // Registrado por el vendedor
    GATEWAY,      // Procesado por pasarela de pago
    CASH_REGISTER
}
