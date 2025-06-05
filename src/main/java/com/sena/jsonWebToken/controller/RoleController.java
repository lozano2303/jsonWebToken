package com.sena.jsonWebToken.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.jsonWebToken.DTO.RoleDTO;
import com.sena.jsonWebToken.model.Role;
import com.sena.jsonWebToken.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    // Obtener rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleService.findById(id);
        return role.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado"));
    }

    // Registrar rol
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RoleDTO roleDTO) {
        String response = roleService.register(roleDTO);
        if (response.equals("Rol registrado correctamente")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody RoleDTO roleDTO) {
        String response = roleService.update(id, roleDTO);
        if (response.equals("Rol actualizado correctamente")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        String response = roleService.deleteById(id);
        if (response.equals("Rol eliminado correctamente")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}