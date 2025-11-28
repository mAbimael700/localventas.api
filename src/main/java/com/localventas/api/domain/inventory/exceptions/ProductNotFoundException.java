package com.localventas.api.domain.inventory.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
    }
}
