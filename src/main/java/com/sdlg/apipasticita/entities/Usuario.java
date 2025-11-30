package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Datos almacenados:
 *  - id -> Identificador único del usuario.
 *  - email -> Correo electrónico usado como dato de acceso y validado como único.
 *  - password -> Contraseña del usuario.
 * Relaciones:
 *  - pacientes -> Relación ManyToMany. Un usuario puede gestionar varios pacientes
 *    y un paciente puede estar asociado a varios usuarios.
 * La relación ManyToMany se implementa mediante la tabla intermedia usuarios_pacientes.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "usuario",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class Usuario {

    /** Identificador del usuario generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Correo electrónico del usuario.
     * Reglas:
     *  - No puede ser vacío.
     *  - Debe ser único en la base de datos.
     *  - Longitud máxima: 190 caracteres.
     */
    @Column(nullable = false, length = 190)
    @NotBlank
    private String email;

    /**
     * Contraseña del usuario.
     * Nota técnica:
     *  Debe almacenarse codificada mediante un algoritmo seguro.
     *  Se usa una longitud amplia para permitir hashes.
     */
    @Column(name = "password", nullable = false, length = 255)
    @JsonIgnore
    private String password;

    /**
     * Lista de pacientes asociados al usuario.
     * Relación ManyToMany:
     *  Un usuario puede tener varios pacientes asociados.
     *  Un mismo paciente puede estar vinculado a más de un usuario.
     * Cascade PERSIST y MERGE:
     *  Permiten propagar operaciones básicas sin eliminar pacientes
     *  cuando se elimina un usuario.
     * Tabla intermedia:
     *  usuarios_pacientes -> usuario_id y paciente_id.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuarios_pacientes",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "paciente_id")
    )
    private List<Paciente> pacientes = new ArrayList<>();
}
