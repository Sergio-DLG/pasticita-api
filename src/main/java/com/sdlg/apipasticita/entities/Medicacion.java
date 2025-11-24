package com.sdlg.apipasticita.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicacion")
@Entity
public class Medicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 150)
    String nombre;

    @Lob
    String descripcion;

    @OneToMany(mappedBy = "medicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Toma> tomas = new ArrayList<>();
}
