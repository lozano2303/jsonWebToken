package com.sena.jsonWebToken.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Role roleID;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    // Constructor sin parámetros
    public User() {
    }

    // Constructor con todos los campos
    public User(int userID, String email, String password, Role roleID, LocalDateTime createdAt) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // --- Métodos de la interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si tienes más roles, puedes retornar una lista, aquí solo uno:
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roleID.getName()));
    }

    @Override
    public String getUsername() {
        // Usamos el email como "username" para Spring Security
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}