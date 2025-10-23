package com.localventas.api.domain.country;

import com.localventas.api.domain.state.State;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;

@Entity
@Table(name = "countries")
@Getter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private HashSet<State> states = new HashSet<>();
}
