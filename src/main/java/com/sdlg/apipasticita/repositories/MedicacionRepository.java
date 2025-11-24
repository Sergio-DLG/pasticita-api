package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Medicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicacionRepository extends JpaRepository<Medicacion, Long> {

    // Buscar por nombre exacto, no lo uso
    List<Medicacion> findByNombre(String nombre);

    // Buscar por nombre que contenga el texto (para autocompletar o buscar)
    List<Medicacion> findByNombreContainingIgnoreCase(String nombre);

}
