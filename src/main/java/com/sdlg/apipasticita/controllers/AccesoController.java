package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.dto.LoginRequest;
import com.sdlg.apipasticita.entities.Usuario;
import com.sdlg.apipasticita.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controlador encargado del registro y acceso de usuarios.
 * Expone los puntos de entrada para crear usuarios y validar credenciales.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/acceso")
public class AccesoController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registro de un nuevo usuario.
     * Recibe email y contraseña en un LoginRequest.
     * Si el email ya existe se devuelve un código 400.
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody LoginRequest request) {
        try {
            Usuario nuevo = usuarioService.registrar(request.getEmail(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            // Error cuando el email ya está en uso
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Inicio de sesión.
     * Se valida email y contraseña usando BCrypt.
     * Si coinciden se devuelve el usuario,
     * si no, se responde con estado 401.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt =
                usuarioService.login(request.getEmail(), request.getPassword());

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}
