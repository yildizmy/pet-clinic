package com.github.yildizmy.repository;

import com.github.yildizmy.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsById(Long id);

    boolean existsByEmailIgnoreCase(String name);
}
