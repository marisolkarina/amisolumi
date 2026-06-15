package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.domain.model.Tejido;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TejidoPortOut {
    Tejido createOut(Tejido tejido);
    Tejido findByIdOut(UUID id);
    List<Tejido> findAllOut();
    Tejido updateOut(UUID id, Tejido tejido);
    void deleteOut(UUID id);
    boolean existsByNombre(String nombre);
    List<Tejido> findByCategoriaNombreOut(String nombre);
    List<Tejido> findByContieneNombre(String nombre);
    List<Tejido> findByPrecioMaxOut(Double precioMax);

}
