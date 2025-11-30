package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un tipo de medicación registrado en el sistema.
 *
 * Estructura:
 *  - id -> Identificador autogenerado.
 *  - nombre -> Nombre de la medicación, obligatorio.
 *  - descripcion -> Información adicional opcional.
 *  - tomas -> Lista de tomas asociadas a esta medicación.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicacion")
public class Medicacion {

    /** Identificador único de la medicación. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la medicación. Este campo es obligatorio y tiene longitud máxima de 100 caracteres. */
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Descripción opcional almacenada como LOB para permitir texto extenso. */
    @Lob
    private String descripcion;

    /**
     * Lista de tomas asociadas a esta medicación.
     * mappedBy indica que la relación está definida en la entidad Toma.
     * cascade = ALL y orphanRemoval = true garantizan que:
     *  - Si se elimina una medicación, se eliminan también sus tomas.
     *  - No pueden quedar tomas huérfanas sin medicación asociada.
     * JsonIgnore evita ciclos de serialización.
     */
    @OneToMany(mappedBy = "medicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Toma> tomas = new ArrayList<>();
}
