package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.model.Pedido;
import com.example.examenFinal.domain.ports.out.PedidoPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import com.example.examenFinal.insfraestructure.mapper.PedidoMapper;
import com.example.examenFinal.insfraestructure.persistance.entity.DetallePedidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.PedidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.TejidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PedidoAdapter implements PedidoPortOut {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TejidoRepository tejidoRepository;
    private final PedidoMapper pedidoMapper;
    private final DetallePedidoRepository detallePedidoRepository;

    public PedidoAdapter(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, TejidoRepository tejidoRepository, PedidoMapper pedidoMapper, DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tejidoRepository = tejidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public Pedido createOut(UUID usuarioId) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setEstado(String.valueOf(EstadoPedido.REGISTRADO));
        pedidoEntity.setTotal(0.0);

        UsuarioEntity usuarioE = usuarioRepository.findById(usuarioId).orElseThrow();
        pedidoEntity.setUsuario(usuarioE);

        pedidoEntity.setDetalles(new ArrayList<>());

        pedidoRepository.save(pedidoEntity);

        Pedido pedido = new Pedido();
        pedido.setId(pedidoEntity.getId());
        pedido.setEstado(pedidoEntity.getEstado());
        pedido.setTotal(pedidoEntity.getTotal());
        pedido.setCreatedAt(pedidoEntity.getCreatedAt());
        pedido.setUpdatedAt(pedidoEntity.getUpdatedAt());
        pedido.setUsuarioId(usuarioE.getId());
        pedido.setDetalles(new ArrayList<>());

        return pedido;
    }

    @Override
    public Pedido findByIdOut(UUID id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con id "+id+" no existe"));
        return pedidoMapper.entityToModel(pedidoEntity);
    }

    @Override
    public List<Pedido> findAllOut() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::entityToModel)
                .toList();
    }



    @Override
    public void deleteOut(UUID id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido addItemOut(UUID pedidoId, UUID tejidoId, Integer cantidad) {

        // se busca el pedido
        PedidoEntity pedidoEntity = pedidoRepository.findById(pedidoId).orElseThrow();

        // se busca el tejido
        TejidoEntity tejidoE = tejidoRepository.findById(tejidoId).orElseThrow();

        // el pedido puede tener detalle_pedido anteriores
        List<DetallePedidoEntity> listaDetalles = pedidoEntity.getDetalles();

        // se busca si existe el tejido en el pedido
        for (DetallePedidoEntity d : listaDetalles) {
            if (d.getTejido().getId().equals(tejidoId)) {
                // si el tejido existe, solo se actualiza la cantidad
                return updateCantidadOut(pedidoId, d.getId(), d.getCantidad()+cantidad);
            }
        }

        // se crea el detalle_pedido
        DetallePedidoEntity detallePedidoEntityNew = new DetallePedidoEntity();
        detallePedidoEntityNew.setCantidad(cantidad);
        detallePedidoEntityNew.setTejido(tejidoE);
        detallePedidoEntityNew.setPedido(pedidoEntity);

        listaDetalles.add(detallePedidoEntityNew);

        // seteamos los nuevos detalles en el pedido
        pedidoEntity.setDetalles(listaDetalles);

        // se guarda el detalle_pedido
        detallePedidoRepository.save(detallePedidoEntityNew);

        // se recalcula el total
        Double total =
                pedidoEntity.getDetalles()
                        .stream()
                        .mapToDouble(
                                d -> d.getCantidad()*d.getTejido().getPrecio()
                        )
                        .sum();
        pedidoEntity.setTotal(total);
        pedidoRepository.save(pedidoEntity);

        return pedidoMapper.entityToModel(pedidoEntity);
    }

    @Override
    public Pedido removeItemOut(UUID pedidoId, UUID detalleId) {
        // se busca el detalle pedido
        DetallePedidoEntity detallePedidoEntity = detallePedidoRepository.findById(detalleId).orElseThrow();
        // se elimina el detalle pedido
        detallePedidoRepository.deleteById(detalleId);

        // se busca el pedido
        PedidoEntity pedidoEntity = pedidoRepository.findById(pedidoId).orElseThrow();

        // el pedido puede tener detalle_pedido anteriores
        List<DetallePedidoEntity> listaDetalles = pedidoEntity.getDetalles();
        listaDetalles.remove(detallePedidoEntity);
        pedidoEntity.setDetalles(listaDetalles);

        // se recalcula el total
        Double total =
                pedidoEntity.getDetalles()
                        .stream()
                        .filter(d -> !d.getId().equals(detalleId))
                        .mapToDouble(
                                d -> d.getCantidad()*d.getTejido().getPrecio()
                        )
                        .sum();
        pedidoEntity.setTotal(total);
        pedidoRepository.save(pedidoEntity);

        return pedidoMapper.entityToModel(pedidoEntity);
    }

    @Override
    public Pedido updateCantidadOut(UUID pedidoId, UUID detalleId, Integer cantidad) {
        // se busca el pedido
        PedidoEntity pedidoEntity = pedidoRepository.findById(pedidoId).orElseThrow();

        // se busca el detalle pedido
        DetallePedidoEntity detallePedidoEntity = detallePedidoRepository.findById(detalleId).orElseThrow();

        detallePedidoEntity.setCantidad(cantidad);
        detallePedidoRepository.save(detallePedidoEntity);

        // se recalcula el total
        Double total =
                pedidoEntity.getDetalles()
                        .stream()
                        .mapToDouble(
                                d -> d.getCantidad()*d.getTejido().getPrecio()
                        )
                        .sum();
        pedidoEntity.setTotal(total);

        pedidoRepository.save(pedidoEntity);

        return pedidoMapper.entityToModel(pedidoEntity);
    }

    @Override
    public List<Pedido> findByUsuarioOut(String username) {
        return pedidoRepository.findByUsuario_UsernameIgnoreCase(username)
                .stream()
                .map(pedidoMapper::entityToModel)
                .toList();
    }
}
