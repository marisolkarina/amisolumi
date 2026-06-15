package com.example.examenFinal.application;

import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.domain.model.Pedido;
import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.in.PedidoPortIn;
import com.example.examenFinal.domain.ports.out.DetallePedidoPortOut;
import com.example.examenFinal.domain.ports.out.PedidoPortOut;
import com.example.examenFinal.domain.ports.out.TejidoPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService implements PedidoPortIn {

    private final PedidoPortOut pedidoPortOut;
    private final UsuarioPortOut usuarioPortOut;
    private final TejidoPortOut tejidoPortOut;
    private final DetallePedidoPortOut detallePedidoPortOut;

    public PedidoService(PedidoPortOut pedidoPortOut, UsuarioPortOut usuarioPortOut, TejidoPortOut tejidoPortOut, DetallePedidoPortOut detallePedidoPortOut) {
        this.pedidoPortOut = pedidoPortOut;
        this.usuarioPortOut = usuarioPortOut;
        this.tejidoPortOut = tejidoPortOut;
        this.detallePedidoPortOut = detallePedidoPortOut;
    }

    @Override
    public Pedido createIn(UUID usuarioId) {
        if (usuarioPortOut.findByIdOut(usuarioId)==null) {
            throw new ResourceNotFoundException("Usuario no existe");
        }
        return pedidoPortOut.createOut(usuarioId);
    }

    @Override
    public Pedido findByIdIn(UUID id) {
        return pedidoPortOut.findByIdOut(id);
    }

    @Override
    public List<Pedido> findAllIn() {
        return pedidoPortOut.findAllOut();
    }

    @Override
    public void deleteIn(UUID id) {
        Pedido pedidoBuscado = pedidoPortOut.findByIdOut(id);
        if (pedidoBuscado == null) throw new ResourceNotFoundException("Pedido con id "+id+" no existe");
        pedidoPortOut.deleteOut(id);
    }

    @Override
    public Pedido addItemIn(UUID pedidoId, UUID tejidoId, Integer cantidad) {
        Pedido pedidoBuscado = pedidoPortOut.findByIdOut(pedidoId);
        if(pedidoBuscado==null) {
            throw new ResourceNotFoundException("Pedido no existe");
        }

        Tejido tejidoBuscado = tejidoPortOut.findByIdOut(tejidoId);
        if(tejidoBuscado==null) {
            throw new ResourceNotFoundException("Tejido no existe");
        }

        return pedidoPortOut.addItemOut(pedidoId, tejidoId, cantidad);
    }

    @Override
    public Pedido removeItemIn(UUID pedidoId, UUID detalleId) {
        Pedido pedidoBuscado = pedidoPortOut.findByIdOut(pedidoId);
        if(pedidoBuscado==null) {
            throw new ResourceNotFoundException("Pedido no existe");
        }
        DetallePedido detallePedidoBuscado = detallePedidoPortOut.findByIdOut(detalleId);
        if(detallePedidoBuscado==null) {
            throw new ResourceNotFoundException("Detalle pedido no existe");
        }
        if (!detallePedidoBuscado.getPedidoId().equals(pedidoId)) {
            throw new ResourceNotFoundException("Detalle pedido con id "+detalleId+" no pertenece al pedido con id "+pedidoId);
        }
        return pedidoPortOut.removeItemOut(pedidoId, detalleId);
    }

    @Override
    public Pedido updateCantidadIn(UUID pedidoId, UUID detalleId, Integer cantidad) {
        Pedido pedidoBuscado = pedidoPortOut.findByIdOut(pedidoId);
        if(pedidoBuscado==null) {
            throw new ResourceNotFoundException("Pedido no existe");
        }

        DetallePedido detallePedidoBuscado = detallePedidoPortOut.findByIdOut(detalleId);
        if(detallePedidoBuscado==null) {
            throw new ResourceNotFoundException("Detalle pedido no existe");
        }

        if (!detallePedidoBuscado.getPedidoId().equals(pedidoId)) {
            throw new ResourceNotFoundException("Detalle pedido con id "+detalleId+" no pertenece al pedido con id "+pedidoId);
        }

        return pedidoPortOut.updateCantidadOut(pedidoId, detalleId, cantidad);
    }

    @Override
    public List<Pedido> findByUsuarioIn(String username) {
        Usuario usuarioBuscado = usuarioPortOut.findByUsername(username);
        if(usuarioBuscado==null) {
            throw new ResourceNotFoundException("Usuario no existe");
        }
        return pedidoPortOut.findByUsuarioOut(username);
    }
}
