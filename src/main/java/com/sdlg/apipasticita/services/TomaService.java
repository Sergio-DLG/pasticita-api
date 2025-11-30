package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Toma;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestionar las tomas de medicación.
 *
 * Define las operaciones principales relacionadas con:
 *  - Listado de tomas por paciente
 *  - Filtrado por intervalos de fechas
 *  - Creación y modificación
 *  - Marcado como tomada
 *  - Eliminación
 */
public interface TomaService {

    /**
     * Devuelve todas las tomas asociadas a un paciente.
     */
    List<Toma> listarPorPaciente(Long pacienteId);

    /**
     * Obtiene las tomas programadas entre dos fechas concretas.
     */
    List<Toma> listarEntreFechas(Long pacienteId, LocalDateTime desde, LocalDateTime hasta);

    /**
     * Obtiene las tomas futuras a partir de una fecha indicada.
     */
    List<Toma> listarFuturas(Long pacienteId, LocalDateTime desde);

    /**
     * Crea una toma para un paciente y una medicación determinada.
     */
    Toma crearToma(Long pacienteId, Long medicacionId, Toma toma);

    /**
     * Marca una toma como tomada y registra la fecha en la que se tomó.
     */
    Toma marcarComoTomada(Long tomaId);

    /**
     * Elimina una toma individual.
     */
    void eliminarToma(Long tomaId);

    /**
     * Actualiza los datos principales de una toma ya existente.
     */
    Toma actualizarToma(Long tomaId, Toma datos);
}
