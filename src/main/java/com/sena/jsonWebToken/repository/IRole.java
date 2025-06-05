package com.sena.jsonWebToken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.jsonWebToken.model.Role;

public interface IRole extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
}