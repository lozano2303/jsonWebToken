package com.sena.jsonWebToken.DTO;

import com.sena.jsonWebToken.model.Role;

public class UserDTO {
    private String name;
    private String email;
    private Role roleID; // como objeto

    public UserDTO() {}

    public UserDTO(String name, String email, Role roleID) {
        this.name = name;
        this.email = email;
        this.roleID = roleID;
    }

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRoleID() { return roleID; }
    public void setRoleID(Role roleID) { this.roleID = roleID; }
}