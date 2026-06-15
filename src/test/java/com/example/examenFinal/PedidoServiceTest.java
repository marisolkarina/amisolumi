package com.example.examenFinal;

import com.example.examenFinal.application.PedidoService;
import com.example.examenFinal.domain.model.DetallePedido;
import com.example.examenFinal.domain.model.Pedido;
import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.out.DetallePedidoPortOut;
import com.example.examenFinal.domain.ports.out.PedidoPortOut;
import com.example.examenFinal.domain.ports.out.TejidoPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoPortOut pedidoPortOut;

    @Mock
    private UsuarioPortOut usuarioPortOut;

    @Mock
    private TejidoPortOut tejidoPortOut;

    @Mock
    private DetallePedidoPortOut detallePedidoPortOut;

    @InjectMocks
    private PedidoService pedidoService;

    private UUID usuarioId;
    private UUID pedidoId;
    private UUID tejidoId;
    private UUID detalleId;
    private Usuario usuario;
    private Pedido pedido;
    private Tejido tejido;
    private DetallePedido detallePedido;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        pedidoId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        tejidoId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
        detalleId = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");

        usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setUsername("marisolpr");
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(100.0);
        pedido.setCreatedAt(new Date());
        pedido.setUpdatedAt(new Date());
        pedido.setUsuarioId(usuarioId);

        tejido = new Tejido();
        tejido.setId(tejidoId);
        tejido.setNombre("Tejido andino");
        tejido.setPrecio(50.0);
        tejido.setUsuarioId(usuarioId);
        tejido.setCategoriasId(List.of(UUID.fromString("123e4567-e89b-12d3-a456-426614174010")));

        detallePedido = new DetallePedido();
        detallePedido.setId(detalleId);
        detallePedido.setCantidad(2);
        detallePedido.setTejidoId(tejidoId);
        detallePedido.setNombreTejido("Tejido andino");
        detallePedido.setPrecioUnitario(50.0);
        detallePedido.setPedidoId(pedidoId);
    }

    @Test
    void createIn_whenUsuarioExists_shouldCreatePedido() {
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuario);
        when(pedidoPortOut.createOut(usuarioId)).thenReturn(pedido);

        Pedido resultado = pedidoService.createIn(usuarioId);

        assertSame(pedido, resultado);
        verify(pedidoPortOut).createOut(usuarioId);
    }

    @Test
    void createIn_whenUsuarioDoesNotExist_shouldThrowResourceNotFoundException() {
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.createIn(usuarioId));

        verify(pedidoPortOut, never()).createOut(usuarioId);
    }

    @Test
    void findByIdIn_shouldDelegateToPort() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);

        Pedido resultado = pedidoService.findByIdIn(pedidoId);

        assertSame(pedido, resultado);
        verify(pedidoPortOut).findByIdOut(pedidoId);
    }

    @Test
    void findAllIn_shouldDelegateToPort() {
        when(pedidoPortOut.findAllOut()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.findAllIn();

        assertEquals(1, resultado.size());
        assertSame(pedido, resultado.get(0));
        verify(pedidoPortOut).findAllOut();
    }

    @Test
    void deleteIn_whenPedidoExists_shouldDelete() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);

        pedidoService.deleteIn(pedidoId);

        verify(pedidoPortOut).deleteOut(pedidoId);
    }

    @Test
    void deleteIn_whenPedidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.deleteIn(pedidoId));

        verify(pedidoPortOut, never()).deleteOut(pedidoId);
    }

    @Test
    void addItemIn_whenEverythingExists_shouldAddItem() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejido);
        when(pedidoPortOut.addItemOut(pedidoId, tejidoId, 3)).thenReturn(pedido);

        Pedido resultado = pedidoService.addItemIn(pedidoId, tejidoId, 3);

        assertSame(pedido, resultado);
        verify(pedidoPortOut).addItemOut(pedidoId, tejidoId, 3);
    }

    @Test
    void addItemIn_whenPedidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.addItemIn(pedidoId, tejidoId, 3));

        verifyNoInteractions(tejidoPortOut);
        verify(pedidoPortOut, never()).addItemOut(pedidoId, tejidoId, 3);
    }

    @Test
    void addItemIn_whenTejidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.addItemIn(pedidoId, tejidoId, 3));

        verify(pedidoPortOut, never()).addItemOut(pedidoId, tejidoId, 3);
    }

    @Test
    void removeItemIn_whenEverythingMatches_shouldRemoveItem() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(detallePedido);
        when(pedidoPortOut.removeItemOut(pedidoId, detalleId)).thenReturn(pedido);

        Pedido resultado = pedidoService.removeItemIn(pedidoId, detalleId);

        assertSame(pedido, resultado);
        verify(pedidoPortOut).removeItemOut(pedidoId, detalleId);
    }

    @Test
    void removeItemIn_whenPedidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.removeItemIn(pedidoId, detalleId));

        verifyNoInteractions(detallePedidoPortOut);
        verify(pedidoPortOut, never()).removeItemOut(pedidoId, detalleId);
    }

    @Test
    void removeItemIn_whenDetalleDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.removeItemIn(pedidoId, detalleId));

        verify(pedidoPortOut, never()).removeItemOut(pedidoId, detalleId);
    }

    @Test
    void removeItemIn_whenDetalleDoesNotBelongToPedido_shouldThrowResourceNotFoundException() {
        DetallePedido detalleDeOtroPedido = new DetallePedido();
        detalleDeOtroPedido.setId(detalleId);
        detalleDeOtroPedido.setPedidoId(UUID.fromString("123e4567-e89b-12d3-a456-426614174099"));

        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(detalleDeOtroPedido);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.removeItemIn(pedidoId, detalleId));

        verify(pedidoPortOut, never()).removeItemOut(pedidoId, detalleId);
    }

    @Test
    void updateCantidadIn_whenEverythingMatches_shouldUpdateQuantity() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(detallePedido);
        when(pedidoPortOut.updateCantidadOut(pedidoId, detalleId, 5)).thenReturn(pedido);

        Pedido resultado = pedidoService.updateCantidadIn(pedidoId, detalleId, 5);

        assertSame(pedido, resultado);
        verify(pedidoPortOut).updateCantidadOut(pedidoId, detalleId, 5);
    }

    @Test
    void updateCantidadIn_whenPedidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.updateCantidadIn(pedidoId, detalleId, 5));

        verifyNoInteractions(detallePedidoPortOut);
        verify(pedidoPortOut, never()).updateCantidadOut(pedidoId, detalleId, 5);
    }

    @Test
    void updateCantidadIn_whenDetalleDoesNotExist_shouldThrowResourceNotFoundException() {
        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.updateCantidadIn(pedidoId, detalleId, 5));

        verify(pedidoPortOut, never()).updateCantidadOut(pedidoId, detalleId, 5);
    }

    @Test
    void updateCantidadIn_whenDetalleDoesNotBelongToPedido_shouldThrowResourceNotFoundException() {
        DetallePedido detalleDeOtroPedido = new DetallePedido();
        detalleDeOtroPedido.setId(detalleId);
        detalleDeOtroPedido.setPedidoId(UUID.fromString("123e4567-e89b-12d3-a456-426614174099"));

        when(pedidoPortOut.findByIdOut(pedidoId)).thenReturn(pedido);
        when(detallePedidoPortOut.findByIdOut(detalleId)).thenReturn(detalleDeOtroPedido);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.updateCantidadIn(pedidoId, detalleId, 5));

        verify(pedidoPortOut, never()).updateCantidadOut(pedidoId, detalleId, 5);
    }

    @Test
    void findByUsuarioIn_whenUsuarioExists_shouldReturnPedidos() {
        when(usuarioPortOut.findByUsername("marisolpr")).thenReturn(usuario);
        when(pedidoPortOut.findByUsuarioOut("marisolpr")).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.findByUsuarioIn("marisolpr");

        assertEquals(1, resultado.size());
        assertSame(pedido, resultado.get(0));
        verify(pedidoPortOut).findByUsuarioOut("marisolpr");
    }

    @Test
    void findByUsuarioIn_whenUsuarioDoesNotExist_shouldThrowResourceNotFoundException() {
        when(usuarioPortOut.findByUsername("marisolpr")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.findByUsuarioIn("marisolpr"));

        verify(pedidoPortOut, never()).findByUsuarioOut("marisolpr");
    }
}
