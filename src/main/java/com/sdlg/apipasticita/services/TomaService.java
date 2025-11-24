package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Toma;

import java.time.LocalDateTime;
import java.util.List;

public interface TomaService {

    List<Toma> listarPorPaciente(Long pacienteId);

    List<Toma> listarEntreFechas(Long pacienteId, LocalDateTime desde, LocalDateTime hasta);

    List<Toma> listarFuturas(Long pacienteId, LocalDateTime desde);

    Toma crearToma(Long pacienteId, Long medicacionId, Toma toma);

    Toma marcarComoTomada(Long tomaId);

    void eliminarToma(Long tomaId);

    Toma actualizarToma(Long tomaId, Toma datos);
}