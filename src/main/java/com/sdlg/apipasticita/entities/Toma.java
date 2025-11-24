package com.sdlg.apipasticita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tomas")
@Entity
public class Toma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    @JsonIgnore
    Paciente paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicacion_id")
    Medicacion medicacion;

    LocalDateTime programadaPara;

    @Column(nullable = false)
    boolean tomada = false;

    LocalDateTime fechaDeToma;

    @Lob
    String notas;
}
