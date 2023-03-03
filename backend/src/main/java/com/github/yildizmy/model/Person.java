package com.github.yildizmy.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = {"email"})
public class Person {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-person"
    )
    @SequenceGenerator(
            name = "sequence-person",
            sequenceName = "sequence_person",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;
}
