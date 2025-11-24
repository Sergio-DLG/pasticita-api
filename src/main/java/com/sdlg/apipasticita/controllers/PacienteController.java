package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{pacienteId}")
    public ResponseEntity<Paciente> obtener(@PathVariable Long pacienteId) {
        Paciente p = pacienteService.obtenerPorId(pacienteId);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{pacienteId}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long pacienteId, @RequestBody Paciente datos) {
        Paciente actualizado = pacienteService.actualizarPaciente(pacienteId, datos);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<?> eliminarDefinitivo(@PathVariable Long pacienteId) {
        pacienteService.eliminarPacienteDefinitivamente(pacienteId);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente eliminado de la base de datos"));
    }
}