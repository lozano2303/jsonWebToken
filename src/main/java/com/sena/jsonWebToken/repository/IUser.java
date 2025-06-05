package com.sena.jsonWebToken.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.jsonWebToken.model.User;

public interface IUser extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u")
    List<User> getListUserActive();

    @Query("""
        SELECT u
        FROM User u
        WHERE (:email IS NULL OR u.email LIKE %:email%)
            AND (:password IS NULL OR u.password = :password)
    """)
    List<User> filterUser(
        @Param("email") String email,
        @Param("password") String password
    );

    // Verificar si un correo ya está registrado
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    // Buscar un usuario por correo electrónico
    Optional<User> findByEmail(String email);
}