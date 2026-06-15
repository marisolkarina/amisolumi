package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.ports.in.DetallePedidoPortIn;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetallePedidoController {
    @Qualifier("detallesService")
    private final DetallePedidoPortIn detallePedidoPortIn;

    public DetallePedidoController(DetallePedidoPortIn detallePedidoPortIn) {
        this.detallePedidoPortIn = detallePedidoPortIn;
    }


}
