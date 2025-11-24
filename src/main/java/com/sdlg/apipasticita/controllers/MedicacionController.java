package com.sdlg.apipasticita.controllers;

import com.sdlg.apipasticita.entities.Medicacion;
import com.sdlg.apipasticita.services.MedicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/medicaciones")
public class MedicacionController {

    @Autowired
    private MedicacionService medicacionService;


    @GetMapping
    public ResponseEntity<List<Medicacion>> buscar(@RequestParam(required = false) String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return ResponseEntity.ok(medicacionService.buscarPorNombre(""));
        }
        return ResponseEntity.ok(medicacionService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Medicacion> crear(@RequestBody Medicacion medicacion) {
        Medicacion creada = medicacionService.crear(medicacion);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicacion> actualizar(@PathVariable Long id, @RequestBody Medicacion datos) {
        Medicacion actualizada = medicacionService.actualizar(id, datos);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        medicacionService.eliminar(id);
        return ResponseEntity.ok().build();
    }


}