package com.github.yildizmy.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"name"})
public class Type {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-type"
    )
    @SequenceGenerator(
            name = "sequence-type",
            sequenceName = "sequence_type",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private Set<Pet> pets = new HashSet<>();

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setType(this);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setType(null);
    }
}
