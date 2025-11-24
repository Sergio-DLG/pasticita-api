package com.sdlg.apipasticita.repositories;

import com.sdlg.apipasticita.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    // Para login
    Optional<Usuario> findByEmail(String email);

    // Para comprobar si ya existe el correo en el registro
    boolean existsByEmail(String email);

    List<Usuario> findByPacientes_Id(Long pacienteId);
}
