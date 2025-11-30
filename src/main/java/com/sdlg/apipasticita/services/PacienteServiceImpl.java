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

    /**
     * Devuelve todos los pacientes asociados a un usuario concreto.
     */
    @Override
    public List<Paciente> listarPorUsuario(Long usuarioId) {
        return pacienteRepository.findAllByUsuarioId(usuarioId);
    }

    /**
     * Crea un paciente nuevo y lo asocia al usuario indicado.
     */
    @Override
    public Paciente crearYAsignarAPUsuario(Long usuarioId, Paciente paciente) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente guardado = pacienteRepository.save(paciente);

        usuario.getPacientes().add(guardado);
        usuarioRepository.save(usuario);

        return guardado;
    }

    /**
     * Asocia un paciente ya existente a un usuario.
     * Si ya estaba asociado no hace nada.
     */
    @Override
    public void asignarPacienteExistenteAUsuario(Long usuarioId, Long pacienteId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        if (!usuario.getPacientes().contains(paciente)) {
            usuario.getPacientes().add(paciente);
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Elimina la relación entre un usuario y un paciente,
     * sin borrar el paciente de la base de datos.
     */
    @Override
    public void desasociarPacienteDeUsuario(Long usuarioId, Long pacienteId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        usuario.getPacientes().remove(paciente);
        paciente.getUsuarios().remove(usuario);

        usuarioRepository.save(usuario);
    }

    /**
     * Obtiene un paciente por su id.
     */
    @Override
    public Paciente obtenerPorId(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    /**
     * Actualiza los campos básicos del paciente.
     */
    @Override
    public Paciente actualizarPaciente(Long pacienteId, Paciente datos) {
        Paciente existente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        existente.setNombre(datos.getNombre());
        existente.setApellidos(datos.getApellidos());
        existente.setFechaNacimiento(datos.getFechaNacimiento());
        existente.setAlergias(datos.getAlergias());

        return pacienteRepository.save(existente);
    }

    /**
     * Elimina un paciente definitivamente de la base de datos.
     * Primero se eliminan todas sus asociaciones con usuarios.
     */
    @Override
    public void eliminarPacienteDefinitivamente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        List<Usuario> usuarios = usuarioRepository.findByPacientes_Id(pacienteId);

        for (Usuario u : usuarios) {
            u.getPacientes().remove(paciente);
            paciente.getUsuarios().remove(u);
        }

        pacienteRepository.delete(paciente);
    }
}
