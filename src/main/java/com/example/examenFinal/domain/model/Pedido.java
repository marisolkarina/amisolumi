package com.example.examenFinal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private UUID id;
    private String estado;
    private Double total;
    private Date createdAt;
    private Date updatedAt;
    private UUID usuarioId;

    private List<DetallePedido> detalles;
}
