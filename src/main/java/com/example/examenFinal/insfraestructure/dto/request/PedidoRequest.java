package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.Pedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PedidoRequest {
    // se crea con estado REGISTRADO
    // se crea con total=0
    // solo se manda el usuario del pedido
    @NotNull(message = "El usuarioId es obligatorio")
    private UUID usuarioId;

//    public Pedido toDomain() {
//        return new Pedido(
//                null,
//                null,
//                null,
//                null,
//                null,
//                usuarioId
//        );
//    }
}
