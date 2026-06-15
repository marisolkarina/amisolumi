package com.example.examenFinal.insfraestructure.mapper;

import com.example.examenFinal.domain.model.Pedido;
import com.example.examenFinal.insfraestructure.persistance.entity.DetallePedidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.PedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
    public Pedido entityToModel(PedidoEntity pedidoEntity) {
        return new Pedido(
                pedidoEntity.getId(),
                pedidoEntity.getEstado(),
                pedidoEntity.getTotal(),
                pedidoEntity.getCreatedAt(),
                pedidoEntity.getUpdatedAt(),
                pedidoEntity.getUsuario().getId(),

                pedidoEntity.getDetalles()
                        .stream()
                        .map(DetallePedidoMapper::entityToModel)
                        .toList()
        );
    }
}
