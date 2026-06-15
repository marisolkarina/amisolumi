package com.example.examenFinal.insfraestructure.mapper;

import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.insfraestructure.persistance.entity.DetallePedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {

    public static DetallePedido entityToModel(DetallePedidoEntity detallePedidoEntity) {
        return new DetallePedido(
                detallePedidoEntity.getId(),
                detallePedidoEntity.getCantidad(),
                detallePedidoEntity.getTejido().getId(),
                detallePedidoEntity.getTejido().getNombre(),
                detallePedidoEntity.getTejido().getPrecio(),
                detallePedidoEntity.getPedido().getId()
        );
    }
}
