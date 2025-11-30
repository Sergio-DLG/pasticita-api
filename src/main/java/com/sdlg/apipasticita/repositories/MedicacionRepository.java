package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Medicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Medicacion.
 */
@Repository
public interface MedicacionRepository extends JpaRepository<Medicacion, Long> {

    /**
     * Devuelve las medicaciones cuyo nombre contiene
     * el texto indicado, ignorando mayúsculas y minúsculas.
     */
    List<Medicacion> findByNombreContainingIgnoreCase(String nombre);
}
