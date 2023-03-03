package com.github.yildizmy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends Person {

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 120, nullable = false)
    private String password;
}
