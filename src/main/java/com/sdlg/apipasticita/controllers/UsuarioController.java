package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.entities.Usuario;
import com.sdlg.apipasticita.services.PacienteService;
import com.sdlg.apipasticita.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    // ------------------------------------------
    //               OPERACIONES USUARIO
    // ------------------------------------------

    /**
     * Comprueba si ya existe un usuario registrado con el email indicado.
     */
    @GetMapping("/existe")
    public ResponseEntity<Map<String, Boolean>> existe(@RequestParam String email) {
        boolean existe = usuarioService.existePorEmail(email);
        return ResponseEntity.ok(Map.of("existe", existe));
    }

    /**
     * Obtiene los datos de un usuario por su identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Actualiza email y/o contraseña de un usuario.
     * El servicio aplica validaciones internas como evitar duplicados de email.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario
    ) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un usuario del sistema.
     * La eliminación no borra los pacientes asociados.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }

    // ------------------------------------------
    //        RELACIÓN USUARIO -> PACIENTE
    // ------------------------------------------

    /**
     * Obtiene la lista de pacientes asociados a un usuario.
     * La relación se gestiona mediante la tabla intermedia usuarios_pacientes.
     */
    @GetMapping("/{usuarioId}/pacientes")
    public ResponseEntity<List<Paciente>> listarPacientesDeUsuario(@PathVariable Long usuarioId) {
        List<Paciente> pacientes = pacienteService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Crea un paciente nuevo y lo asocia al usuario indicado.
     */
    @PostMapping("/{usuarioId}/pacientes")
    public ResponseEntity<Paciente> crearPacienteParaUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Paciente paciente
    ) {
        Paciente creado = pacienteService.crearYAsignarAPUsuario(usuarioId, paciente);
        return ResponseEntity.ok(creado);
    }

    /**
     * Asocia a un usuario un paciente ya existente en la base de datos.
     */
    @PostMapping("/{usuarioId}/pacientes/{pacienteId}")
    public ResponseEntity<?> asignarPacienteExistente(
            @PathVariable Long usuarioId,
            @PathVariable Long pacienteId
    ) {
        pacienteService.asignarPacienteExistenteAUsuario(usuarioId, pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente añadido correctamente"));
    }

    /**
     * Elimina la relación entre un usuario y un paciente,
     * sin borrar al paciente de la base de datos.
     */
    @DeleteMapping("/{usuarioId}/pacientes/{pacienteId}")
    public ResponseEntity<?> desasociarPacienteDeUsuario(
            @PathVariable Long usuarioId,
            @PathVariable Long pacienteId
    ) {
        pacienteService.desasociarPacienteDeUsuario(usuarioId, pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente desvinculado del usuario"));
    }
}
