package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Toma;
import com.sdlg.apipasticita.services.TomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar tomas de medicación:
 * creación, consulta, actualización, marcado y eliminación.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TomaController {

    @Autowired
    private TomaService tomaService;

    /**
     * Devuelve todas las tomas programadas para el día actual.
     */
    @GetMapping("/pacientes/{pacienteId}/tomas/hoy")
    public ResponseEntity<List<Toma>> listarTomasDeHoy(@PathVariable Long pacienteId) {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atStartOfDay();
        LocalDateTime fin = hoy.atTime(LocalTime.MAX);

        List<Toma> tomas = tomaService.listarEntreFechas(pacienteId, inicio, fin);
        return ResponseEntity.ok(tomas);
    }

    /**
     * Devuelve las tomas futuras a partir del momento actual.
     */
    @GetMapping("/pacientes/{pacienteId}/tomas/futuras")
    public ResponseEntity<List<Toma>> listarFuturas(@PathVariable Long pacienteId) {
        List<Toma> tomas = tomaService.listarFuturas(pacienteId, LocalDateTime.now());
        return ResponseEntity.ok(tomas);
    }

    /**
     * Crea una nueva toma asociada a un paciente y a una medicación.
     * El id de la medicación se pasa como parámetro.
     */
    @PostMapping("/pacientes/{pacienteId}/tomas")
    public ResponseEntity<Toma> crearToma(
            @PathVariable Long pacienteId,
            @RequestParam Long medicacionId,
            @RequestBody Toma toma
    ) {
        Toma creada = tomaService.crearToma(pacienteId, medicacionId, toma);
        return ResponseEntity.ok(creada);
    }

    /**
     * Devuelve todas las tomas programadas para una fecha concreta.
     */
    @GetMapping("/pacientes/{pacienteId}/tomas")
    public ResponseEntity<List<Toma>> listarTomasPorFecha(
            @PathVariable Long pacienteId,
            @RequestParam String fecha
    ) {
        LocalDate dia = LocalDate.parse(fecha);
        LocalDateTime inicio = dia.atStartOfDay();
        LocalDateTime fin = dia.atTime(LocalTime.MAX);

        List<Toma> tomas = tomaService.listarEntreFechas(pacienteId, inicio, fin);
        return ResponseEntity.ok(tomas);
    }

    /**
     * Marca una toma como realizada.
     * También establece la fecha y hora de realización.
     */
    @PatchMapping("/tomas/{tomaId}/marcar")
    public ResponseEntity<Toma> marcarComoTomada(@PathVariable Long tomaId) {
        Toma actualizada = tomaService.marcarComoTomada(tomaId);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Actualiza los datos principales de una toma:
     * horario, notas o medicación asociada.
     */
    @PutMapping("/tomas/{tomaId}")
    public ResponseEntity<Toma> actualizarToma(
            @PathVariable Long tomaId,
            @RequestBody Toma datos
    ) {
        Toma actualizada = tomaService.actualizarToma(tomaId, datos);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una toma por su identificador.
     */
    @DeleteMapping("/tomas/{tomaId}")
    public ResponseEntity<?> eliminar(@PathVariable Long tomaId) {
        tomaService.eliminarToma(tomaId);
        return ResponseEntity.ok(Map.of("mensaje", "Toma eliminada correctamente"));
    }
}
