package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Paciente.
 * Permite buscar pacientes asociados a un usuario concreto.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Obtiene todos los pacientes vinculados a un usuario,
     * utilizando la relación ManyToMany y la tabla intermedia.
     */
    @Query("""
            select p
            from Paciente p
            join p.usuarios u
            where u.id = :usuarioId
            """)
    List<Paciente> findAllByUsuarioId(Long usuarioId);
}
