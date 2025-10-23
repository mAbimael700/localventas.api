package com.localventas.api.domain.state;

import com.localventas.api.domain.country.Country;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "states")
@Getter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
}

