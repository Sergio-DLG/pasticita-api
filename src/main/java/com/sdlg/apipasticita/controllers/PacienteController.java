package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para operaciones directas sobre pacientes:
 * obtener, actualizar o eliminar de forma definitiva.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    /**
     * Devuelve un paciente por su identificador.
     */
    @GetMapping("/{pacienteId}")
    public ResponseEntity<Paciente> obtener(@PathVariable Long pacienteId) {
        Paciente p = pacienteService.obtenerPorId(pacienteId);
        return ResponseEntity.ok(p);
    }

    /**
     * Actualiza los datos de un paciente existente.
     */
    @PutMapping("/{pacienteId}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long pacienteId,
            @RequestBody Paciente datos
    ) {
        Paciente actualizado = pacienteService.actualizarPaciente(pacienteId, datos);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina por completo un paciente de la base de datos.
     * También elimina asociaciones con usuarios.
     */
    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<?> eliminarDefinitivo(@PathVariable Long pacienteId) {
        pacienteService.eliminarPacienteDefinitivamente(pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente eliminado de la base de datos"));
    }
}
