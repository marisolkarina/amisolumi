package com.example.examenFinal.domain.ports.in;

import com.example.examenFinal.domain.model.DetallePedido;

import java.util.List;
import java.util.UUID;

public interface DetallePedidoPortIn {
    DetallePedido createIn(DetallePedido detallePedido);
    DetallePedido findByIdIn(UUID id);
    List<DetallePedido> findAllIn();
    DetallePedido updateIn(UUID id, DetallePedido detallePedido);
    void deleteIn(UUID id);

}
