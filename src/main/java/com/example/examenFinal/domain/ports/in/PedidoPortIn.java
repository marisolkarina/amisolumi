package com.example.examenFinal.domain.ports.in;

import com.example.examenFinal.domain.model.Pedido;

import java.util.List;
import java.util.UUID;

public interface PedidoPortIn {
    Pedido createIn(UUID usuarioId);
    Pedido findByIdIn(UUID id);
    List<Pedido> findAllIn();
    void deleteIn(UUID id);

    Pedido addItemIn(UUID pedidoId, UUID tejidoId, Integer cantidad);
    Pedido removeItemIn(UUID pedidoId, UUID detalleId);
    Pedido updateCantidadIn(UUID pedidoId, UUID detalleId, Integer cantidad);
    List<Pedido> findByUsuarioIn(String username);
}
