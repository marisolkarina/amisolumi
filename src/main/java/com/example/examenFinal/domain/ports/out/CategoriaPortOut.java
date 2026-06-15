package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.domain.model.Categoria;

import java.util.List;
import java.util.UUID;

public interface CategoriaPortOut {
    Categoria createOut(Categoria categoria);
    Categoria findByIdOut(UUID id);
    List<Categoria> findAllOut();
    Categoria updateOut(UUID id, Categoria categoria);
    void deleteOut(UUID id);
    boolean existsByNombre(String nombre);
    boolean existsAllById(List<UUID> categoriasId);
}
