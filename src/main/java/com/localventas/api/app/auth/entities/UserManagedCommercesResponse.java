package com.localventas.api.app.auth.entities;

import java.util.List;

public record UserManagedCommercesResponse(
        List<ManagedCommerceDetail> commerces
) {
}
record ManagedCommerceDetail(
        Long id,
        String name,
        List<UserCommerceRole> roles
){
}
record UserCommerceRole(
        Long id,
        String name
){}
