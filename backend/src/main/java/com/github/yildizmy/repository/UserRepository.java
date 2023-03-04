package com.github.yildizmy.repository;

import com.github.yildizmy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long id);

    boolean existsByUsernameIgnoreCase(String name);
}
