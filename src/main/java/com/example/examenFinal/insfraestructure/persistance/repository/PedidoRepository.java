package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {
    List<PedidoEntity> findByUsuario_UsernameIgnoreCase(String username);
}
