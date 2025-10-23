package com.localventas.api.domain.person;

import com.localventas.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @OneToMany(mappedBy = "personalInfo", fetch = FetchType.LAZY)
    private List<User> users;
}
