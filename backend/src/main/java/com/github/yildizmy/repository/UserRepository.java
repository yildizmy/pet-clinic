package com.github.yildizmy.repository;

import com.github.yildizmy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String name);

    Optional<User> findByUsername(String username);
}
