package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Toma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TomaRepository extends JpaRepository<Toma, Long> {


    // Todas las tomas de un paciente
    List<Toma> findByPaciente_Id(Long pacienteId);

    // Tomas programadas entre dos fechas (por ejemplo, para mostrar las del día actual)
    List<Toma> findByPaciente_IdAndProgramadaParaBetweenOrderByProgramadaParaAsc(Long pacienteId, LocalDateTime desde, LocalDateTime hasta);

    // Tomas futuras
    List<Toma> findByPaciente_IdAndProgramadaParaAfterOrderByProgramadaParaAsc(Long pacienteId, LocalDateTime fecha);

    // Tomas ya realizadas, no lo uso
    List<Toma> findByPaciente_IdAndTomadaTrueOrderByProgramadaParaDesc(Long pacienteId);

    // Tomas pendientes, no lo uso
    List<Toma> findByPaciente_IdAndTomadaFalseOrderByProgramadaParaAsc(Long pacienteId);
}
