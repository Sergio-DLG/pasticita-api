package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Medicacion;

import java.util.List;

/**
 * Define las operaciones de gestión para la entidad Medicacion.
 *
 * Incluye creación, búsqueda por nombre, lectura por id,
 * actualización y eliminación.
 */
public interface MedicacionService {

    /**
     * Crea y guarda un nuevo registro de medicación.
     */
    Medicacion crear(Medicacion medicacion);

    /**
     * Busca medicaciones cuyo nombre contenga el texto dado.
     */
    List<Medicacion> buscarPorNombre(String texto);

    /**
     * Obtiene una medicación por su id.
     */
    Medicacion obtenerPorId(Long id);

    /**
     * Actualiza los datos de una medicación existente.
     */
    Medicacion actualizar(Long id, Medicacion datos);

    /**
     * Elimina una medicación por su id.
     */
    void eliminar(Long id);
}
