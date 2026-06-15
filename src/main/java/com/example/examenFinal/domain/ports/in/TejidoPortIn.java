package com.example.examenFinal.domain.ports.in;

import com.example.examenFinal.domain.model.Tejido;

import java.util.List;
import java.util.UUID;

public interface TejidoPortIn {
    Tejido createIn(Tejido tejido);
    Tejido findByIdIn(UUID id);
    List<Tejido> findAllIn();
    Tejido updateIn(UUID id, Tejido tejido);
    void deleteIn(UUID id);
    List<Tejido> findByCategoriaNombreIn(String nombreCategoria);
    List<Tejido> findByContieneNombreIn(String nombre);
    List<Tejido> findByPrecioMaxIn (Double precioMax);

}
