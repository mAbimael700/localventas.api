package com.localventas.api.domain.inventory.inventorymovementtype;

import lombok.Getter;

public enum InventoryMovementType {
    // Entradas
    INITIAL_STOCK("Inventario Inicial", true),
    PURCHASE("Compra a Proveedor", true),
    RETURN_FROM_CLIENT("Devolución de Cliente", true),
    ADJUSTMENT_POSITIVE("Ajuste Positivo", true),
    TRANSFER_IN("Transferencia Entrada", true),

    // Salidas
    FORMAL_SALE("Venta Formal", false),        // ECOMMERCE o CASH_REGISTER
    DAMAGED("Producto Dañado", false),
    EXPIRED("Producto Vencido", false),
    ADJUSTMENT_NEGATIVE("Ajuste Negativo", false),
    TRANSFER_OUT("Transferencia Salida", false),
    THEFT("Robo/Pérdida", false),

    // Reconciliación (ventas manuales)
    MANUAL_SALE_RECONCILIATION("Reconciliación Venta Manual", false);

    @Getter
    private final String description;
    private final boolean isIncrease;

    InventoryMovementType(String description, boolean isIncrease) {
        this.description = description;
        this.isIncrease = isIncrease;
    }

    public boolean isIncrease() {
        return isIncrease;
    }

    public boolean isDecrease() {
        return !isIncrease;
    }
}
