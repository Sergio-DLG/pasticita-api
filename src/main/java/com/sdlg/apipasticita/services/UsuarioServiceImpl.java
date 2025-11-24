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

    @Override
    public Usuario registrar(String email, String password) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        Usuario u = new Usuario();
        u.setEmail(email);

        // Hash de la contraseña con BCrypt
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        u.setPassword(hashed);

        return usuarioRepository.save(u);
    }

    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email).filter(u -> {
            String hashed = u.getPassword();
            return BCrypt.checkpw(password, hashed);
        });
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (datosActualizados.getEmail() != null && !datosActualizados.getEmail().equals(usuario.getEmail())) {

            if (usuarioRepository.existsByEmail(datosActualizados.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }
            usuario.setEmail(datosActualizados.getEmail());
        }


        if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
            String hashed = BCrypt.hashpw(datosActualizados.getPassword(), BCrypt.gensalt());
            usuario.setPassword(hashed);
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
