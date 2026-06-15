package com.example.examenFinal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    private UUID id;
    private Integer cantidad;
    private UUID tejidoId;
    private String nombreTejido;
    private Double precioUnitario;
    private UUID pedidoId;

}
