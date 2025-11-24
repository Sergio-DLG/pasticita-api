package com.sdlg.apipasticita.services;

import com.sdlg.apipasticita.entities.Medicacion;

import java.util.List;

public interface MedicacionService {

    Medicacion crear(Medicacion medicacion);

    List<Medicacion> buscarPorNombre(String texto);

    Medicacion obtenerPorId(Long id);

    Medicacion actualizar(Long id, Medicacion datos);

    void eliminar(Long id);

}