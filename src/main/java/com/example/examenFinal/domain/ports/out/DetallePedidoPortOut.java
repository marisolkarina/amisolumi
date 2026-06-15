package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.domain.model.Pedido;

import java.util.List;
import java.util.UUID;

public interface DetallePedidoPortOut {
    DetallePedido createOut(DetallePedido detallePedido);
    DetallePedido findByIdOut(UUID id);
    List<DetallePedido> findAllOut();
    DetallePedido updateOut(UUID id, DetallePedido detallePedido);
    void deleteOut(UUID id);

}
