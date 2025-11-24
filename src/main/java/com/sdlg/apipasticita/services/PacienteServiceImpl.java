package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Paciente;
import com.sdlg.apipasticita.entities.Usuario;
import com.sdlg.apipasticita.exception.ResourceNotFoundException;
import com.sdlg.apipasticita.repositories.PacienteRepository;
import com.sdlg.apipasticita.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Paciente> listarPorUsuario(Long usuarioId) {
        return pacienteRepository.findAllByUsuarioId(usuarioId);
    }

    @Override
    public Paciente crearYAsignarAPUsuario(Long usuarioId, Paciente paciente) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente guardado = pacienteRepository.save(paciente);

        usuario.getPacientes().add(guardado);
        usuarioRepository.save(usuario);

        return guardado;
    }

    @Override
    public void asignarPacienteExistenteAUsuario(Long usuarioId, Long pacienteId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        if (!usuario.getPacientes().contains(paciente)) {
            usuario.getPacientes().add(paciente);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public void desasociarPacienteDeUsuario(Long usuarioId, Long pacienteId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        usuario.getPacientes().remove(paciente);
        paciente.getUsuarios().remove(usuario);

        usuarioRepository.save(usuario);
    }

    @Override
    public Paciente obtenerPorId(Long pacienteId) {
        return pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    @Override
    public Paciente actualizarPaciente(Long pacienteId, Paciente datos) {
        Paciente existente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        existente.setNombre(datos.getNombre());
        existente.setApellidos(datos.getApellidos());
        existente.setFechaNacimiento(datos.getFechaNacimiento());
        existente.setAlergias(datos.getAlergias());

        return pacienteRepository.save(existente);
    }

    @Override
    public void eliminarPacienteDefinitivamente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        List<Usuario> usuarios = usuarioRepository.findByPacientes_Id(pacienteId);
        for (Usuario u : usuarios) {
            u.getPacientes().remove(paciente);
            paciente.getUsuarios().remove(u);
        }

        pacienteRepository.delete(paciente);
    }
}
