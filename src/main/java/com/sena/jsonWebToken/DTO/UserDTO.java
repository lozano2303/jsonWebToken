package com.sena.jsonWebToken.DTO;

import com.sena.jsonWebToken.model.Role;

public class UserDTO {
    private String email;
    private String password;
    private Role roleID; // como objeto

    public UserDTO() {}

    public UserDTO(String email, String password, Role roleID) {
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRoleID() { return roleID; }
    public void setRoleID(Role roleID) { this.roleID = roleID; }
}