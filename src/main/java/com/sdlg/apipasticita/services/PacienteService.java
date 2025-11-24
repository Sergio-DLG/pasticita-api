package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> listarPorUsuario(Long usuarioId);

    Paciente crearYAsignarAPUsuario(Long usuarioId, Paciente paciente);

    void asignarPacienteExistenteAUsuario(Long usuarioId, Long pacienteId);

    void desasociarPacienteDeUsuario(Long usuarioId, Long pacienteId);

    Paciente obtenerPorId(Long pacienteId);

    Paciente actualizarPaciente(Long pacienteId, Paciente datos);

    void eliminarPacienteDefinitivamente(Long pacienteId);
}