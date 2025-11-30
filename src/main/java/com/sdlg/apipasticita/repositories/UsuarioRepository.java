package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 * Permite buscar usuarios por email y consultar
 * relaciones con pacientes.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por email.
     * Utilizado para el proceso de login.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Indica si existe un usuario registrado con un email concreto.
     * Utilizado para validar el registro o actualizaciones.
     */
    boolean existsByEmail(String email);

    /**
     * Devuelve usuarios asociados a un paciente concreto.
     * Utilizado al eliminar pacientes para limpiar asociaciones.
     */
    List<Usuario> findByPacientes_Id(Long pacienteId);
}
