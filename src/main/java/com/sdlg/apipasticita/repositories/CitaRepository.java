package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad Cita.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Devuelve todas las citas asociadas a un paciente.
     */
    List<Cita> findByPaciente_Id(Long pacienteId);

    /**
     * Devuelve las citas cuya fecha es igual o posterior
     * a la indicada, ordenadas por fecha ascendente.
     */
    List<Cita> findByPaciente_IdAndFechaCitaGreaterThanEqualOrderByFechaCitaAsc(
            Long pacienteId,
            LocalDateTime fechaDesde
    );
}
