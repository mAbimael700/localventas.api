package com.localventas.api.domain.sale;

public enum SaleOrigin {
    MANUAL,        // Venta registrada manualmente sin caja
    ECOMMERCE,     // Venta originada desde el e-commerce
    CASH_REGISTER  // Venta realizada a través de caja
}
