package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Todas las citas de un paciente
    List<Cita> findByPaciente_Id(Long pacienteId);

    // Citas futuras (desde una fecha)
    List<Cita> findByPaciente_IdAndFechaCitaGreaterThanEqualOrderByFechaCitaAsc(Long pacienteId, LocalDateTime fechaDesde);

    // Citas pasadas, no lo uso
    List<Cita> findByPaciente_IdAndFechaCitaBeforeOrderByFechaCitaDesc(Long pacienteId, LocalDateTime fechaHasta);


}
