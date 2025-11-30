package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Usuario;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra un nuevo usuario.
     * Comprueba que el email no exista y almacena la contraseña en formato hash.
     */
    @Override
    public Usuario registrar(String email, String password) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        Usuario u = new Usuario();
        u.setEmail(email);

        // Genera un hash seguro de la contraseña
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        u.setPassword(hashed);

        return usuarioRepository.save(u);
    }

    /**
     * Devuelve el usuario si el email existe y la contraseña proporcionada coincide
     * con la almacenada (comparada mediante BCrypt).
     */
    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email).filter(u -> {
            String hashed = u.getPassword();
            return BCrypt.checkpw(password, hashed);
        });
    }

    /**
     * Obtiene un usuario por id o lanza excepción si no existe.
     */
    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    /**
     * Indica si un email ya está registrado.
     */
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Actualiza email y/o contraseña del usuario.
     * Si se cambia el email se verifica que no esté registrado por otro usuario.
     * La contraseña nueva se almacena siempre en hash.
     */
    @Override
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Actualiza email si es distinto y no está ocupado
        if (datosActualizados.getEmail() != null &&
                !datosActualizados.getEmail().equals(usuario.getEmail())) {

            if (usuarioRepository.existsByEmail(datosActualizados.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }
            usuario.setEmail(datosActualizados.getEmail());
        }

        // Actualiza contraseña si se ha enviado una nueva
        if (datosActualizados.getPassword() != null &&
                !datosActualizados.getPassword().isBlank()) {

            String hashed = BCrypt.hashpw(datosActualizados.getPassword(), BCrypt.gensalt());
            usuario.setPassword(hashed);
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por id o lanza excepción si no existe.
     */
    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
