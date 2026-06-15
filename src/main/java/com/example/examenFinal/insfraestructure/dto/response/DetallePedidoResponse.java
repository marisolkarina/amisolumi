package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.DetallePedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DetallePedidoResponse {
    private UUID id;
    private Integer cantidad;
    private UUID tejidoId;
    private String nombreTejido;
    private Double precioUnitario;
    private UUID pedidoId;
//    private Date createdAt;
//    private Date updatedAt;

    public static DetallePedidoResponse fromDomain(DetallePedido detallePedido) {
        return new DetallePedidoResponse(
                detallePedido.getId(),
                detallePedido.getCantidad(),
                detallePedido.getTejidoId(),
                detallePedido.getNombreTejido(),
                detallePedido.getPrecioUnitario(),
                detallePedido.getPedidoId()
        );
    }
}
