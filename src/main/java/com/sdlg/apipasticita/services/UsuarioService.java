package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Usuario;

import java.util.Optional;

/**
 * Define las operaciones principales relacionadas con usuarios.
 */
public interface UsuarioService {

    /**
     * Registra un nuevo usuario con email y contraseña.
     */
    Usuario registrar(String email, String password);

    /**
     * Intenta iniciar sesión con email y contraseña.
     * Devuelve Optional vacío si las credenciales no coinciden.
     */
    Optional<Usuario> login(String email, String password);

    /**
     * Obtiene un usuario por id.
     */
    Usuario obtenerPorId(Long id);

    /**
     * Indica si ya existe un usuario con el email dado.
     */
    boolean existePorEmail(String email);

    /**
     * Actualiza los datos del usuario (email y contraseña).
     */
    Usuario actualizarUsuario(Long id, Usuario datos);

    /**
     * Elimina un usuario por su id.
     */
    void eliminarUsuario(Long id);
}
