package com.sena.jsonWebToken.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.jsonWebToken.DTO.RoleDTO;
import com.sena.jsonWebToken.model.Role;
import com.sena.jsonWebToken.repository.IRole;

@Service
public class RoleService {

    @Autowired
    private IRole roleRepository;

    // Listar todos los roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    // Buscar rol por ID
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    // Verificar si un rol existe por ID
    public boolean existsById(int id) {
        return roleRepository.existsById(id);
    }

    // Registrar un nuevo rol
    @Transactional
    public String register(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName())) {
            return "El nombre del rol ya existe";
        }

        try {
            Role role = convertToModel(roleDTO);
            roleRepository.save(role);
            return "Rol registrado correctamente";
        } catch (DataAccessException e) {
            return "Error de base de datos al guardar el rol";
        } catch (Exception e) {
            return "Error inesperado al guardar el rol";
        }
    }

    // Actualizar rol por ID
    @Transactional
    public String update(int id, RoleDTO roleDTO) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (!roleOptional.isPresent()) {
            return "Rol no encontrado";
        }

        try {
            Role role = roleOptional.get();
            if (!role.getName().equals(roleDTO.getName()) && roleRepository.existsByName(roleDTO.getName())) {
                return "El nombre del rol ya existe";
            }
            role.setName(roleDTO.getName());
            roleRepository.save(role);
            return "Rol actualizado correctamente";
        } catch (DataAccessException e) {
            return "Error de base de datos al actualizar el rol";
        } catch (Exception e) {
            return "Error inesperado al actualizar el rol";
        }
    }

    // Eliminar rol por ID
    @Transactional
    public String deleteById(int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (!roleOptional.isPresent()) {
            return "Rol no encontrado";
        }
        try {
            roleRepository.deleteById(id);
            return "Rol eliminado correctamente";
        } catch (DataAccessException e) {
            return "Error de base de datos al eliminar el rol";
        } catch (Exception e) {
            return "Error inesperado al eliminar el rol";
        }
    }

    // Convertir DTO a entidad
    public Role convertToModel(RoleDTO roleDTO) {
        return new Role(0, roleDTO.getName());
    }

    // Convertir entidad a DTO
    public RoleDTO convertToDTO(Role role) {
        return new RoleDTO(role.getName());
    }
}