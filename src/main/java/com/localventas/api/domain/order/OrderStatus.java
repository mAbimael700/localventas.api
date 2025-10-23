package com.localventas.api.domain.order;

public enum OrderStatus {
    // Estados para ECOMMERCE
    PENDING,      // Esperando confirmación del comercio
    CONFIRMED,    // Confirmado, esperando pago
    CANCELLED,    // Cancelado antes de pagar

    // Estados para LOCAL (ya pagado)
    PREPARING,    // En preparación
    READY,        // Listo para entrega/recoger
    IN_TRANSIT,   // En camino (si es delivery)
    DELIVERED,    // Entregado
    COMPLETED     // Completado
}
