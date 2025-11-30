package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Cita;
import com.sdlg.apipasticita.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las citas asociadas a los pacientes.
 * Ofrece operaciones para listar, crear, actualizar y eliminar citas.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CitaController {

    @Autowired
    private CitaService citaService;

    /**
     * Lista todas las citas de un paciente desde una fecha concreta.
     * Si el parámetro desde no se recibe, se usa el inicio del día actual.
     */
    @GetMapping("/pacientes/{pacienteId}/citas")
    public ResponseEntity<List<Cita>> listarCitasDesde(
            @PathVariable Long pacienteId,
            @RequestParam(required = false) String desde
    ) {
        LocalDateTime fechaDesde;

        if (desde != null) {
            fechaDesde = LocalDateTime.parse(desde);
        } else {
            fechaDesde = LocalDate.now().atStartOfDay();
        }

        List<Cita> citas = citaService.listarPorPacienteDesde(pacienteId, fechaDesde);
        return ResponseEntity.ok(citas);
    }

    /**
     * Lista todas las citas de un paciente sin filtrar por fecha.
     */
    @GetMapping("/pacientes/{pacienteId}/citas/todas")
    public ResponseEntity<List<Cita>> listarTodas(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.listarTodasPorPaciente(pacienteId));
    }

    /**
     * Crea una cita nueva asociada a un paciente.
     */
    @PostMapping("/pacientes/{pacienteId}/citas")
    public ResponseEntity<Cita> crear(
            @PathVariable Long pacienteId,
            @RequestBody Cita cita
    ) {
        Cita creada = citaService.crearCita(pacienteId, cita);
        return ResponseEntity.ok(creada);
    }

    /**
     * Actualiza los datos de una cita existente.
     */
    @PutMapping("/citas/{citaId}")
    public ResponseEntity<Cita> actualizar(
            @PathVariable Long citaId,
            @RequestBody Cita citaDatos
    ) {
        Cita actualizada = citaService.actualizarCita(citaId, citaDatos);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una cita por su identificador.
     */
    @DeleteMapping("/citas/{citaId}")
    public ResponseEntity<?> eliminar(@PathVariable Long citaId) {
        citaService.eliminarCita(citaId);
        return ResponseEntity.ok(Map.of("mensaje", "Cita eliminada correctamente"));
    }
}
