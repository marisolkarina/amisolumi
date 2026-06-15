package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.DetallePedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DetallePedidoRequest {
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que 0")
    private Integer cantidad;

    @NotNull(message = "El tejidoId es obligatorio")
    private UUID tejidoId;
    @NotNull(message = "El pedidoId es obligatorio")
    private UUID pedidoId;

//    public DetallePedido toDomain() {
//        return new DetallePedido(
//                null,
//                cantidad,
//                tejidoId,
//                pedidoId
//        );
//    }
}
