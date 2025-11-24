package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Medicacion;
import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.entities.Toma;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.MedicacionRepository;
import com.sdlg.apipasticita.repositories.PacienteRepository;
import com.sdlg.apipasticita.repositories.TomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TomaServiceImpl implements TomaService {

    @Autowired
    private TomaRepository tomaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicacionRepository medicacionRepository;

    @Override
    public List<Toma> listarPorPaciente(Long pacienteId) {
        return tomaRepository.findByPaciente_Id(pacienteId);
    }

    @Override
    public List<Toma> listarEntreFechas(Long pacienteId, LocalDateTime desde, LocalDateTime hasta) {
        return tomaRepository.findByPaciente_IdAndProgramadaParaBetweenOrderByProgramadaParaAsc(pacienteId, desde, hasta);
    }

    @Override
    public List<Toma> listarFuturas(Long pacienteId, LocalDateTime desde) {
        return tomaRepository.findByPaciente_IdAndProgramadaParaAfterOrderByProgramadaParaAsc(pacienteId, desde);
    }

    @Override
    public Toma crearToma(Long pacienteId, Long medicacionId, Toma toma) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        Medicacion medicacion = medicacionRepository.findById(medicacionId).orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));

        toma.setPaciente(paciente);
        toma.setMedicacion(medicacion);
        return tomaRepository.save(toma);
    }

    @Override
    public Toma marcarComoTomada(Long tomaId) {
        Toma toma = tomaRepository.findById(tomaId).orElseThrow(() -> new ResourceNotFoundException("Toma no encontrada"));
        toma.setTomada(true);
        toma.setFechaDeToma(LocalDateTime.now());
        return tomaRepository.save(toma);
    }

    @Override
    public void eliminarToma(Long tomaId) {
        if (!tomaRepository.existsById(tomaId)) {
            throw new ResourceNotFoundException("Toma no encontrada");
        }
        tomaRepository.deleteById(tomaId);
    }

    @Override
    public Toma actualizarToma(Long tomaId, Toma datos) {
        Toma existente = tomaRepository.findById(tomaId).orElseThrow(() -> new ResourceNotFoundException("Toma no encontrada"));

        existente.setProgramadaPara(datos.getProgramadaPara());
        existente.setNotas(datos.getNotas());

        if (datos.getMedicacion() != null && datos.getMedicacion().getId() != null) {
            Medicacion medicacion = medicacionRepository.findById(datos.getMedicacion().getId()).orElseThrow(() -> new ResourceNotFoundException("Medicación no encontrada"));
            existente.setMedicacion(medicacion);
        }

        existente.setTomada(datos.isTomada());
        existente.setFechaDeToma(datos.getFechaDeToma());

        return tomaRepository.save(existente);
    }
}
