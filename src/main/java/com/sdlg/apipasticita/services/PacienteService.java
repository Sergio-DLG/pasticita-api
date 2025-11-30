package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Paciente;

import java.util.List;

/**
 * Servicio que define las operaciones relacionadas con los pacientes
 * y su asociación con los usuarios.
 */
public interface PacienteService {

    /**
     * Devuelve todos los pacientes asociados a un usuario concreto.
     */
    List<Paciente> listarPorUsuario(Long usuarioId);

    /**
     * Crea un paciente y lo asocia automáticamente al usuario indicado.
     */
    Paciente crearYAsignarAPUsuario(Long usuarioId, Paciente paciente);

    /**
     * Asocia un paciente ya existente a un usuario.
     */
    void asignarPacienteExistenteAUsuario(Long usuarioId, Long pacienteId);

    /**
     * Elimina la relación entre un usuario y un paciente concreto,
     * sin borrar el paciente de la base de datos.
     */
    void desasociarPacienteDeUsuario(Long usuarioId, Long pacienteId);

    /**
     * Recupera un paciente por su id.
     */
    Paciente obtenerPorId(Long pacienteId);

    /**
     * Actualiza los datos básicos de un paciente existente.
     */
    Paciente actualizarPaciente(Long pacienteId, Paciente datos);

    /**
     * Elimina totalmente un paciente de la base de datos,
     * independientemente de cuántos usuarios estuvieran asociados a él.
     */
    void eliminarPacienteDefinitivamente(Long pacienteId);
}
