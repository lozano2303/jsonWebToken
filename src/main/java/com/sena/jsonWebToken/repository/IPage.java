package com.sena.jsonWebToken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.jsonWebToken.model.Page;

public interface IPage extends JpaRepository<Page, Integer> {
    boolean existsByName(String name);
}