package com.example.examenFinal.application;

import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.domain.ports.in.DetallePedidoPortIn;
import com.example.examenFinal.domain.ports.out.DetallePedidoPortOut;
import com.example.examenFinal.domain.ports.out.PedidoPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DetallePedidoService implements DetallePedidoPortIn {

    private final DetallePedidoPortOut detallePedidoPortOut;
    private final PedidoPortOut pedidoPortOut;

    public DetallePedidoService(DetallePedidoPortOut detallePedidoPortOut, PedidoPortOut pedidoPortOut) {
        this.detallePedidoPortOut = detallePedidoPortOut;
        this.pedidoPortOut = pedidoPortOut;
    }

    @Override
    public DetallePedido createIn(DetallePedido detallePedido) {

        return null;
    }

    @Override
    public DetallePedido findByIdIn(UUID id) {
        return detallePedidoPortOut.findByIdOut(id);
    }

    @Override
    public List<DetallePedido> findAllIn() {
        return detallePedidoPortOut.findAllOut();
    }

    @Override
    public DetallePedido updateIn(UUID id, DetallePedido detallePedido) {
        return null;
    }

    @Override
    public void deleteIn(UUID id) {
        DetallePedido detallePedidoBuscado = detallePedidoPortOut.findByIdOut(id);
        if (detallePedidoBuscado==null) {
            throw new ResourceNotFoundException("DetallePedido con id "+id+" no existe");
        }
        detallePedidoPortOut.deleteOut(id);
    }
}
