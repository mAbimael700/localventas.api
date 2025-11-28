package com.localventas.api.domain.commercepermission;

public enum CommercePermissionType {
    VIEW,           // Ver/Leer
    CREATE,         // Crear
    UPDATE,         // Actualizar
    DELETE,         // Eliminar
    EXECUTE,        // Ejecutar acciones especiales
    MANAGE,         // Gestión completa (todos los permisos)
    EXPORT,         // Exportar datos
    IMPORT,         // Importar datos
    APPROVE         // Aprobar transacciones
}
