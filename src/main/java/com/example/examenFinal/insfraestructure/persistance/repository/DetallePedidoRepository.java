package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, UUID> {
}
