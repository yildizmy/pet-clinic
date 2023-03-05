package com.github.yildizmy.repository;

import com.github.yildizmy.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    boolean existsById(Long id);

    List<Pet> findAllByUserId(long userId);
}
