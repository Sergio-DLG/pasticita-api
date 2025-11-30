package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Cita;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestionar citas de pacientes.
 */
public interface CitaService {

    /**
     * Lista citas de un paciente desde una fecha concreta.
     */
    List<Cita> listarPorPacienteDesde(Long pacienteId, LocalDateTime desde);

    /**
     * Lista todas las citas de un paciente.
     */
    List<Cita> listarTodasPorPaciente(Long pacienteId);

    /**
     * Crea una cita asociada a un paciente.
     */
    Cita crearCita(Long pacienteId, Cita cita);

    /**
     * Actualiza una cita existente.
     */
    Cita actualizarCita(Long citaId, Cita citaDatos);

    /**
     * Elimina una cita por su identificador.
     */
    void eliminarCita(Long citaId);
}
