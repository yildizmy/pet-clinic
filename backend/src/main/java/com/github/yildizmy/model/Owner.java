package com.github.yildizmy.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Owner extends Person {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Pet> pets = new HashSet<>();

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
        pet.setOwner(null);
    }
}
