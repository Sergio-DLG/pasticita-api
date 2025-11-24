package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Cita;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {

    List<Cita> listarPorPacienteDesde(Long pacienteId, LocalDateTime desde);

    List<Cita> listarTodasPorPaciente(Long pacienteId);

    Cita crearCita(Long pacienteId, Cita cita);

    Cita actualizarCita(Long citaId, Cita citaDatos);

    void eliminarCita(Long citaId);
}
