package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {
    Optional<CategoriaEntity> findByNombreIgnoreCase(String nombre);
//    List<CategoriaEntity> findAllById(List<UUID> categoriasId);
}
