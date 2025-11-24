package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Usuario registrar(String email, String password);

    Optional<Usuario> login(String email, String password);

    Usuario obtenerPorId(Long id);

    boolean existePorEmail(String email);

    Usuario actualizarUsuario(Long id, Usuario datos);

    void eliminarUsuario(Long id);
}
