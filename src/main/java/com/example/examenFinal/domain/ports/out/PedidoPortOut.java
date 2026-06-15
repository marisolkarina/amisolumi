package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.domain.model.Pedido;

import java.util.List;
import java.util.UUID;

public interface PedidoPortOut {
    Pedido createOut(UUID usuarioId);
    Pedido findByIdOut(UUID id);
    List<Pedido> findAllOut();
    void deleteOut(UUID id);
    Pedido addItemOut(UUID pedidoId, UUID tejidoId, Integer cantidad);
    Pedido removeItemOut(UUID pedidoId, UUID detalleId);
    Pedido updateCantidadOut(UUID pedidoId, UUID detalleId, Integer cantidad);
    List<Pedido> findByUsuarioOut(String username);
}
