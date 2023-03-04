package com.github.yildizmy.repository;

import com.github.yildizmy.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    boolean existsById(Long id);

    boolean existsByNameIgnoreCase(String name);
}
