package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un paciente registrado en el sistema.
 * Estructura principal:
 *  - id -> Identificador único generado automáticamente.
 *  - nombre y apellidos -> Datos básicos obligatorios.
 *  - fechaNacimiento -> Fecha de nacimiento opcional.
 *  - alergias -> Información adicional opcional (almacenada como LOB).
 *  - usuarios -> Relación ManyToMany con Usuario.
 *  - citas -> Citas médicas asociadas al paciente.
 *  - tomas -> Tomas de medicación vinculadas al paciente.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente {

    /** Identificador único del paciente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del paciente. Obligatorio y con límite de 100 caracteres. */
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Apellidos del paciente. Obligatorio y con límite de 100 caracteres. */
    @Column(nullable = false, length = 100)
    private String apellidos;

    /** Fecha de nacimiento opcional. Se almacena como LocalDate. */
    private LocalDate fechaNacimiento;

    /** Información sobre alergias del paciente. Puede contener texto extenso. */
    @Lob
    private String alergias;

    /**
     * Relación ManyToMany con usuarios.
     * Un paciente puede pertenecer a varios usuarios y viceversa.
     * mappedBy indica que la relación está gestionada desde la entidad Usuario.
     * JsonIgnore evita ciclos durante la serialización.
     */
    @ManyToMany(mappedBy = "pacientes")
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();

    /**
     * Lista de citas del paciente.
     * Cascade ALL y orphanRemoval true garantizan que:
     *  - Si se elimina el paciente, también se eliminan sus citas.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    /**
     * Lista de tomas de medicación asociadas al paciente.
     * Se eliminan automáticamente si el paciente deja de existir.
     */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Toma> tomas = new ArrayList<>();
}
