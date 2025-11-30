package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa una cita asociada a un paciente.
 * Estructura básica:
 *  - id -> Identificador autogenerado.
 *  - paciente -> Relación muchos a uno con la entidad Paciente.
 *  - fechaCita -> Fecha y hora programadas para la cita.
 *  - descripcion -> Información breve y opcional.
 *  - anotacion -> Campo de texto extenso y opcional.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "citas")
public class Cita {

    /** Identificador único de la cita. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Paciente al que pertenece la cita.
     * La anotación JsonIgnore evita ciclos de serialización y reduce datos innecesarios.
     * La relación es obligatoria (optional = false).
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    @JsonIgnore
    private Paciente paciente;

    /** Fecha y hora programadas para la cita. Este campo es obligatorio. */
    @Column(nullable = false)
    private LocalDateTime fechaCita;

    /** Descripción breve y opcional de la cita. Máximo 255 caracteres. */
    @Column(length = 255)
    private String descripcion;

    /** Anotación larga y opcional. Se almacena como texto tipo LOB. */
    @Lob
    private String anotacion;
}
