package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa una toma programada de medicación para un paciente.
 * Estructura:
 *  - id -> Identificador único.
 *  - paciente -> Relación ManyToOne. Cada toma pertenece a un paciente.
 *  - medicacion -> Medicación asociada a la toma.
 *  - programadaPara -> Fecha y hora programada en la que debe tomarse.
 *  - tomada -> Indica si la toma ha sido completada.
 *  - fechaDeToma -> Fecha real en la que se tomó (solo si tomada es true).
 *  - notas -> Texto opcional con detalles adicionales.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tomas")
public class Toma {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Paciente al que pertenece esta toma.
     * Relación ManyToOne:
     *  Varias tomas pueden pertenecer al mismo paciente.
     * JsonIgnore evita problemas de serialización por referencias circulares.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    @JsonIgnore
    private Paciente paciente;

    /**
     * Medicación asociada a esta toma.
     * Relación ManyToOne:
     *  Varias tomas pueden corresponder a la misma medicación.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "medicacion_id")
    private Medicacion medicacion;

    /**
     * Fecha y hora programada en la que debe realizarse la toma.
     */
    private LocalDateTime programadaPara;

    /**
     * Indica si la toma ha sido marcada como realizada.
     * Por defecto es false.
     */
    @Column(nullable = false)
    private boolean tomada = false;

    /**
     * Fecha y hora en la que realmente se tomó la medicación.
     * Puede ser null si todavía no ha sido marcada como tomada.
     */
    private LocalDateTime fechaDeToma;

    /**
     * Campo opcional para añadir notas adicionales sobre la toma.
     * Se declara como LOB para permitir texto más largo.
     */
    @Lob
    private String notas;
}
