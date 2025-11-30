package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Medicacion;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.MedicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio que gestiona la lógica de negocio
 * relacionada con la entidad Medicacion.
 */
@Service
public class MedicacionServiceImpl implements MedicacionService {

    @Autowired
    private MedicacionRepository medicacionRepository;

    /**
     * Guarda una nueva medicación.
     */
    @Override
    public Medicacion crear(Medicacion medicacion) {
        return medicacionRepository.save(medicacion);
    }

    /**
     * Busca medicaciones cuyo nombre contenga el texto proporcionado.
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     */
    @Override
    public List<Medicacion> buscarPorNombre(String texto) {
        return medicacionRepository.findByNombreContainingIgnoreCase(texto);
    }

    /**
     * Obtiene una medicación concreta a partir de su id.
     * Lanza excepción si no existe.
     */
    @Override
    public Medicacion obtenerPorId(Long id) {
        return medicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));
    }

    /**
     * Actualiza los datos de una medicación existente.
     * Permite modificar el nombre y la descripción.
     */
    @Override
    public Medicacion actualizar(Long id, Medicacion datos) {
        Medicacion existente = medicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));

        if (datos.getNombre() != null && !datos.getNombre().isBlank()) {
            existente.setNombre(datos.getNombre());
        }

        existente.setDescripcion(datos.getDescripcion());

        return medicacionRepository.save(existente);
    }

    /**
     * Elimina una medicación por su id.
     * Lanza excepción si no existe.
     */
    @Override
    public void eliminar(Long id) {
        Medicacion existente = medicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));

        medicacionRepository.delete(existente);
    }
}
