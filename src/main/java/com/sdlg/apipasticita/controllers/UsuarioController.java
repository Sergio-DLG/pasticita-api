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

    // ───────────────────────────
    //           USUARIO
    // ───────────────────────────

    @GetMapping("/existe")
    public ResponseEntity<Map<String, Boolean>> existe(@RequestParam String email) {
        boolean existe = usuarioService.existePorEmail(email);
        return ResponseEntity.ok(Map.of("existe", existe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }

    // ───────────────────────────
    //    RELACIÓN USUARIO–PACIENTE
    // ───────────────────────────

    @GetMapping("/{usuarioId}/pacientes")
    public ResponseEntity<List<Paciente>> listarPacientesDeUsuario(@PathVariable Long usuarioId) {
        List<Paciente> pacientes = pacienteService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping("/{usuarioId}/pacientes")
    public ResponseEntity<Paciente> crearPacienteParaUsuario(@PathVariable Long usuarioId, @RequestBody Paciente paciente) {
        Paciente creado = pacienteService.crearYAsignarAPUsuario(usuarioId, paciente);
        return ResponseEntity.ok(creado);
    }

    @PostMapping("/{usuarioId}/pacientes/{pacienteId}")
    public ResponseEntity<?> asignarPacienteExistente(@PathVariable Long usuarioId, @PathVariable Long pacienteId) {
        pacienteService.asignarPacienteExistenteAUsuario(usuarioId, pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente añadido correctamente"));
    }


    @DeleteMapping("/{usuarioId}/pacientes/{pacienteId}")
    public ResponseEntity<?> desasociarPacienteDeUsuario(@PathVariable Long usuarioId, @PathVariable Long pacienteId) {
        pacienteService.desasociarPacienteDeUsuario(usuarioId, pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente desvinculado del usuario"));
    }
}
