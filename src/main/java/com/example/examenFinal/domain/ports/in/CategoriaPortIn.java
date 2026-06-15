package com.example.examenFinal.domain.ports.in;

import com.example.examenFinal.domain.model.Categoria;

import java.util.List;
import java.util.UUID;

public interface CategoriaPortIn {
    Categoria createIn(Categoria categoria);
    Categoria findByIdIn(UUID id);
    List<Categoria> findAllIn();
    Categoria updateIn(UUID id, Categoria categoria);
    void deleteIn(UUID id);
}
