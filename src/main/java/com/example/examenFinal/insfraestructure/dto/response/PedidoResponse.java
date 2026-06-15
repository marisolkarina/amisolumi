package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PedidoResponse {
    private UUID id;
    private String estado;
    private Double total;
    private Date createdAt;
    private Date updatedAt;
    private UUID usuarioId;
    private List<DetallePedidoResponse> detalles;

    public static PedidoResponse fromDomain(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getCreatedAt(),
                pedido.getUpdatedAt(),
                pedido.getUsuarioId(),
                pedido.getDetalles()
                        .stream()
                        .map(DetallePedidoResponse::fromDomain)
                        .toList()
        );
    }
}
