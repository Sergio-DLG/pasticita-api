package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Medicacion;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.MedicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicacionServiceImpl implements MedicacionService {

    @Autowired
    private MedicacionRepository medicacionRepository;

    @Override
    public Medicacion crear(Medicacion medicacion) {
        return medicacionRepository.save(medicacion);
    }

    @Override
    public List<Medicacion> buscarPorNombre(String texto) {
        return medicacionRepository.findByNombreContainingIgnoreCase(texto);
    }

    @Override
    public Medicacion obtenerPorId(Long id) {
        return medicacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));
    }

    @Override
    public Medicacion actualizar(Long id, Medicacion datos) {
        Medicacion existente = medicacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));

        if (datos.getNombre() != null && !datos.getNombre().isBlank()) {
            existente.setNombre(datos.getNombre());
        }
        existente.setDescripcion(datos.getDescripcion());

        return medicacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Medicacion existente = medicacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));
        medicacionRepository.delete(existente);
    }
}