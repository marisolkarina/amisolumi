package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.domain.ports.out.DetallePedidoPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import com.example.examenFinal.insfraestructure.mapper.DetallePedidoMapper;
import com.example.examenFinal.insfraestructure.persistance.entity.DetallePedidoEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.DetallePedidoRepository;
import com.example.examenFinal.insfraestructure.persistance.repository.PedidoRepository;
import com.example.examenFinal.insfraestructure.persistance.repository.TejidoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DetallePedidoAdapter implements DetallePedidoPortOut {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final TejidoRepository tejidoRepository;

    public DetallePedidoAdapter(DetallePedidoRepository detallePedidoRepository, PedidoRepository pedidoRepository, TejidoRepository tejidoRepository, DetallePedidoMapper detallePedidoMapper) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.tejidoRepository = tejidoRepository;
    }

    @Override
    public DetallePedido createOut(DetallePedido detallePedido) {
        return null;
    }

    @Override
    public DetallePedido findByIdOut(UUID id) {
        DetallePedidoEntity detallePedidoEntity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle pedido con id "+id+" no existe"));
        return DetallePedidoMapper.entityToModel(detallePedidoEntity);
    }

    @Override
    public List<DetallePedido> findAllOut() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(DetallePedidoMapper::entityToModel)
                .toList();
    }

    @Override
    public DetallePedido updateOut(UUID id, DetallePedido detallePedido) {
        return null;
    }

    @Override
    public void deleteOut(UUID id) {
        detallePedidoRepository.deleteById(id);
    }
}
