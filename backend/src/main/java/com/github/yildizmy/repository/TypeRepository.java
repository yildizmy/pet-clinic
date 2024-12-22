package com.github.yildizmy.repository;

import com.github.yildizmy.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    boolean existsByNameIgnoreCase(String name);
}
