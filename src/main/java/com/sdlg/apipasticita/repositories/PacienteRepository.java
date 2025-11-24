package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Devuelve los pacientes asociados a un usuario concreto (por la tabla intermedia usuarios_pacientes)
    @Query("""
            select p 
            from Paciente p 
            join p.usuarios u 
            where u.id = :usuarioId
            """)
    List<Paciente> findAllByUsuarioId(Long usuarioId);
}

