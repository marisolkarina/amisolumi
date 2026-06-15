package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.TejidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TejidoRepository extends JpaRepository<TejidoEntity, UUID> {
    Optional<TejidoEntity> findByNombreIgnoreCase(String nombre);
    List<TejidoEntity> findByCategorias_NombreIgnoreCase(String nombre);
    List<TejidoEntity> findByNombreContainingIgnoreCase(String nombre);
    List<TejidoEntity> findByPrecioLessThanEqual(Double precio);
}
