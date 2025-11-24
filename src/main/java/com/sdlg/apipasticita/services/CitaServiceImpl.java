package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Cita;
import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.CitaRepository;
import com.sdlg.apipasticita.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Cita> listarPorPacienteDesde(Long pacienteId, LocalDateTime desde) {
        return citaRepository.findByPaciente_IdAndFechaCitaGreaterThanEqualOrderByFechaCitaAsc(pacienteId, desde);
    }

    @Override
    public List<Cita> listarTodasPorPaciente(Long pacienteId) {
        return citaRepository.findByPaciente_Id(pacienteId);
    }

    @Override
    public Cita crearCita(Long pacienteId, Cita cita) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        cita.setPaciente(paciente);
        return citaRepository.save(cita);
    }

    @Override
    public Cita actualizarCita(Long citaId, Cita citaDatos) {
        Cita existente = citaRepository.findById(citaId).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        existente.setFechaCita(citaDatos.getFechaCita());
        existente.setDescripcion(citaDatos.getDescripcion());
        existente.setAnotacion(citaDatos.getAnotacion());

        return citaRepository.save(existente);
    }

    @Override
    public void eliminarCita(Long citaId) {
        if (!citaRepository.existsById(citaId)) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }
        citaRepository.deleteById(citaId);
    }
}