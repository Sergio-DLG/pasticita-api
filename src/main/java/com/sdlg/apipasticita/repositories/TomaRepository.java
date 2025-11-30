package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Toma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad Toma.
 * Permite consultar tomas por paciente y por fecha.
 */
@Repository
public interface TomaRepository extends JpaRepository<Toma, Long> {

    /**
     * Devuelve todas las tomas asociadas a un paciente.
     */
    List<Toma> findByPaciente_Id(Long pacienteId);

    /**
     * Devuelve tomas dentro de un rango de fechas,
     * ordenadas por fecha programada.
     */
    List<Toma> findByPaciente_IdAndProgramadaParaBetweenOrderByProgramadaParaAsc(
            Long pacienteId,
            LocalDateTime desde,
            LocalDateTime hasta
    );

    /**
     * Devuelve tomas futuras a partir de una fecha,
     * ordenadas por fecha programada.
     */
    List<Toma> findByPaciente_IdAndProgramadaParaAfterOrderByProgramadaParaAsc(
            Long pacienteId,
            LocalDateTime fecha
    );
}
