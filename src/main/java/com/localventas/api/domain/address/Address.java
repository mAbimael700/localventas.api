package com.localventas.api.domain.address;

import com.localventas.api.domain.country.Country;
import com.localventas.api.domain.state.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @Column(name = "internal_number")
    private String internalNumber;

    @Column(name = "external_number")
    private String externalNumber;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;

    private String locality;

    private Country country;

    private State state;

    @Column
    private String reference;

    // Para distinguir si es una dirección predefinida del vendedor o temporal del comprador
    @Enumerated(EnumType.STRING)
    private AddressType type;
}
