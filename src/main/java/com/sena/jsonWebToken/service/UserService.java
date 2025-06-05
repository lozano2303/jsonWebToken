package com.sena.jsonWebToken.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.jsonWebToken.DTO.ResponseDTO;
import com.sena.jsonWebToken.DTO.UserDTO;
import com.sena.jsonWebToken.model.Role;
import com.sena.jsonWebToken.model.User;
import com.sena.jsonWebToken.repository.IRole;
import com.sena.jsonWebToken.repository.IUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Autowired
    private IUser userRepository;

    private static final String SECRET_KEY = "mi-clave-secreta-muy-segura-no-puedo-creer-por-que-es-tan-segura-guau-guau-oh-oh-oh-me-vengo-que-rico-12345678910-@-@-/";

    @Autowired
    private IRole roleRepository;

    // Listar todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    // Verificar si un usuario existe por ID
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    // Filtrar usuarios por correo y contraseña (ya no usa name ni status)
    public List<User> filterUser(String email, String password) {
        return userRepository.filterUser(email, password);
    }

    // Registro de usuario
    @Transactional
    public ResponseDTO register(UserDTO userDTO) {
        // Validar que el rol exista
        Optional<Role> roleEntity = roleRepository.findById(userDTO.getRoleID().getRoleID());
        if (!roleEntity.isPresent()) {
            return new ResponseDTO("error", "Rol no encontrado");
        }

        // Verificar si el correo ya está registrado
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return new ResponseDTO("error", "El correo electrónico ya está registrado");
        }

        // Validaciones adicionales
        if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return new ResponseDTO("error", "El formato del correo electrónico no es válido");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 8) {
            return new ResponseDTO("error", "La contraseña debe tener al menos 8 caracteres");
        }

        try {
            // Convertir DTO a entidad y guardar
            User userEntity = convertToModel(userDTO);
            userEntity.setRoleID(roleEntity.get());
            userRepository.save(userEntity);

            return new ResponseDTO("success", "Usuario registrado correctamente");
        } catch (DataAccessException e) {
            return new ResponseDTO("error", "Error de base de datos al guardar el usuario");
        } catch (Exception e) {
            return new ResponseDTO("error", "Error inesperado al guardar el usuario");
        }
    }

    // Inicio de sesión
    public ResponseDTO login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return new ResponseDTO("error", "El correo electrónico no está registrado");
        }

        User userEntity = optionalUser.get();

        // Verificar la contraseña hasheada (usa BCrypt)
        if (!BCrypt.checkpw(password, userEntity.getPassword())) {
            return new ResponseDTO("error", "La contraseña es incorrecta");
        }

        // Generar un token
        String token = generateToken(userEntity);

        // Si las credenciales son correctas, devolver un éxito con el token y el usuario
        return new ResponseDTO("success", "Inicio de sesión exitoso", token, userEntity);
    }

    private String generateToken(User userEntity) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Eliminar usuario por ID (borrado lógico)
    @Transactional
public ResponseDTO deleteById(int id) {
    if (!userRepository.existsById(id)) {
        return new ResponseDTO("error", "Usuario no encontrado");
    }

    try {
        userRepository.deleteById(id);
        return new ResponseDTO("success", "Usuario eliminado correctamente");
    } catch (DataAccessException e) {
        return new ResponseDTO("error", "Error de base de datos al eliminar el usuario");
    } catch (Exception e) {
        return new ResponseDTO("error", "Error inesperado al eliminar el usuario");
    }
}

    // Reactivar usuario por ID
    @Transactional
    public ResponseDTO reactivateUser(int id) {
        Optional<User> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) {
            return new ResponseDTO("error", "Usuario no encontrado");
        }

        try {
            User userToReactivate = userEntity.get();
            // Si usas reactivación lógica, aquí podrías implementar un campo extra (por ejemplo, isDeleted)
            userRepository.save(userToReactivate);

            return new ResponseDTO("success", "Usuario reactivado correctamente");
        } catch (DataAccessException e) {
            return new ResponseDTO("error", "Error de base de datos al reactivar el usuario");
        } catch (Exception e) {
            return new ResponseDTO("error", "Error inesperado al reactivar el usuario");
        }
    }

    // Actualizar usuario por ID
    @Transactional
    public ResponseDTO update(int id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            return new ResponseDTO("error", "Usuario no encontrado");
        }

        try {
            User userEntity = userOptional.get();

            // Validar que el rol exista
            Optional<Role> roleEntity = roleRepository.findById(userDTO.getRoleID().getRoleID());
            if (!roleEntity.isPresent()) {
                return new ResponseDTO("error", "Rol no encontrado");
            }

            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
                if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    return new ResponseDTO("error", "El formato del correo electrónico no es válido");
                }
                userEntity.setEmail(userDTO.getEmail());
            }

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                if (userDTO.getPassword().length() < 8) {
                    return new ResponseDTO("error", "La contraseña debe tener al menos 8 caracteres");
                }
                userEntity.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
            }

            userEntity.setRoleID(roleEntity.get());
            userRepository.save(userEntity);

            return new ResponseDTO("success", "Usuario actualizado correctamente");
        } catch (DataAccessException e) {
            return new ResponseDTO("error", "Error de base de datos al actualizar el usuario");
        } catch (Exception e) {
            return new ResponseDTO("error", "Error inesperado al actualizar el usuario");
        }
    }

    // Convertir entidad a DTO
    public UserDTO convertToDTO(User userEntity) {
        return new UserDTO(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoleID()
        );
    }

    // Convertir DTO a entidad
    public User convertToModel(UserDTO userDTO) {
        Optional<Role> roleEntity = roleRepository.findById(userDTO.getRoleID().getRoleID());
        if (!roleEntity.isPresent()) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        return new User(
                0, // ID autogenerado
                userDTO.getEmail(),
                BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()),
                roleEntity.get(),
                LocalDateTime.now()
        );
    }
}