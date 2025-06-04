package com.sena.jsonWebToken.DTO;

public class RoleDTO {
    private String name;

    // Constructor sin parámetros
    public RoleDTO() {
    }

    // Constructor con todos los campos
    public RoleDTO(String name) {
        this.name = name;
    }

    // Getter y setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}