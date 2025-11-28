package com.localventas.api.domain.commerce.service;

import com.localventas.api.domain.address.Address;
import com.localventas.api.domain.commerce.entities.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommerceService {
    /**
     * Obtiene información de un comercio
     * @param commerceId ID del comercio
     * @return Optional con el comercio si existe
     */
    Optional<Commerce> getCommerceInfo(Long commerceId);

    /**
     * Crea un nuevo comercio con su owner
     * @param request Datos del comercio
     * @param userOwnerId ID del usuario owner
     * @return Commerce creado con su empleado owner
     */
    Commerce createCommerce(CommerceCreationRequest request, Long userOwnerId);

    /**
     * Actualiza información de un comercio
     * @param commerceId ID del comercio
     * @param request Datos a actualizar
     * @return Optional con el comercio actualizado
     */
    Optional<Commerce> updateCommerce(Long commerceId, CommerceUpdateRequest request);

    Set<Address> getCommerceAddress(Long commerceId);

    boolean addCommerceAddress(Long commerceId, CommerceAddressCreationRequest addressRequest);

    boolean updateCommerceAddress(Long addressId, CommerceAddressUpdateRequest addressUpdateRequest);

    boolean removeCommerceAddress(Long commerceId, Long addressId);

    /**
     * Obtiene todos los comercios de un usuario
     * @param userId ID del usuario
     * @return Lista de comercios donde el usuario es owner
     */
    List<Commerce> getCommercesByOwner(Long userId);
}
