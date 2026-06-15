package com.example.examenFinal;

import com.example.examenFinal.application.TejidoService;
import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.out.CategoriaPortOut;
import com.example.examenFinal.domain.ports.out.TejidoPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.exception.DuplicateResourceException;
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
class TejidoServiceTest {

    @Mock
    private TejidoPortOut tejidoPortOut;

    @Mock
    private UsuarioPortOut usuarioPortOut;

    @Mock
    private CategoriaPortOut categoriaPortOut;

    @InjectMocks
    private TejidoService tejidoService;

    private Usuario usuarioEncontrado;
    private Tejido tejido;
    private UUID tejidoId;
    private UUID usuarioId;
    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        tejidoId = UUID.fromString("123e4567-e89b-12d3-a456-426614174111");
        usuarioId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        categoriaId = UUID.fromString("123e4567-e89b-12d3-a456-426614174222");

        usuarioEncontrado = new Usuario();
        usuarioEncontrado.setId(usuarioId);
        usuarioEncontrado.setNombres("Marisol");
        usuarioEncontrado.setApellidos("Pachauri Rivera");
        usuarioEncontrado.setEmail("marisol@gmail.com");
        usuarioEncontrado.setPassword("123456");
        usuarioEncontrado.setCelular("923585964");
        usuarioEncontrado.setDireccion("Av. Ciruelos 100");
        usuarioEncontrado.setDni("72924244");
        usuarioEncontrado.setUsername("marisolpr");
        usuarioEncontrado.setFechaNacimiento(new Date());
        usuarioEncontrado.setRol("ADMIN");
        usuarioEncontrado.setCreatedAt(new Date());
        usuarioEncontrado.setUpdatedAt(new Date());

        tejido = new Tejido();
        tejido.setId(tejidoId);
        tejido.setNombre("Osito Ted");
        tejido.setDescripcion("Amigurumi de 15 cm color marron claro.");
        tejido.setPrecio(80.0);
        tejido.setImagen("ositoted.png");
        tejido.setTiempoProduccion(7);
        tejido.setCreatedAt(new Date());
        tejido.setUpdatedAt(new Date());
        tejido.setUsuarioId(usuarioId);
        tejido.setCategoriasId(List.of(categoriaId));
    }

    @Test
    void createIn_whenDataIsValid_shouldCreateTejido() {
        when(tejidoPortOut.existsByNombre(tejido.getNombre())).thenReturn(false);
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioEncontrado);
        when(categoriaPortOut.existsAllById(tejido.getCategoriasId())).thenReturn(true);
        when(tejidoPortOut.createOut(tejido)).thenReturn(tejido);

        Tejido resultado = tejidoService.createIn(tejido);

        assertSame(tejido, resultado);
        verify(tejidoPortOut).createOut(tejido);
    }

    @Test
    void createIn_whenNombreExists_shouldThrowDuplicateResourceException() {
        when(tejidoPortOut.existsByNombre(tejido.getNombre())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> tejidoService.createIn(tejido));

        verifyNoInteractions(usuarioPortOut, categoriaPortOut);
        verify(tejidoPortOut, never()).createOut(tejido);
    }

    @Test
    void createIn_whenUsuarioDoesNotExist_shouldThrowResourceNotFoundException() {
        when(tejidoPortOut.existsByNombre(tejido.getNombre())).thenReturn(false);
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> tejidoService.createIn(tejido));

        verify(categoriaPortOut, never()).existsAllById(tejido.getCategoriasId());
        verify(tejidoPortOut, never()).createOut(tejido);
    }

    @Test
    void createIn_whenCategoriasDoNotExist_shouldThrowResourceNotFoundException() {
        when(tejidoPortOut.existsByNombre(tejido.getNombre())).thenReturn(false);
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioEncontrado);
        when(categoriaPortOut.existsAllById(tejido.getCategoriasId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> tejidoService.createIn(tejido));

        verify(tejidoPortOut, never()).createOut(tejido);
    }

    @Test
    void findByIdIn_shouldDelegateToPort() {
        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejido);

        Tejido resultado = tejidoService.findByIdIn(tejidoId);

        assertSame(tejido, resultado);
        verify(tejidoPortOut).findByIdOut(tejidoId);
    }

    @Test
    void findAllIn_shouldDelegateToPort() {
        when(tejidoPortOut.findAllOut()).thenReturn(List.of(tejido));

        List<Tejido> resultado = tejidoService.findAllIn();

        assertEquals(1, resultado.size());
        assertSame(tejido, resultado.get(0));
        verify(tejidoPortOut).findAllOut();
    }

    @Test
    void updateIn_whenDataIsValid_shouldUpdateTejido() {
        Tejido tejidoActual = new Tejido();
        tejidoActual.setNombre("Tejido anterior");

        Tejido tejidoActualizado = new Tejido();
        tejidoActualizado.setNombre("Tejido nuevo");
        tejidoActualizado.setCategoriasId(List.of(categoriaId));

        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejidoActual);
        when(tejidoPortOut.existsByNombre(tejidoActualizado.getNombre())).thenReturn(false);
        when(categoriaPortOut.existsAllById(tejidoActualizado.getCategoriasId())).thenReturn(true);
        when(tejidoPortOut.updateOut(tejidoId, tejidoActualizado)).thenReturn(tejidoActualizado);

        Tejido resultado = tejidoService.updateIn(tejidoId, tejidoActualizado);

        assertSame(tejidoActualizado, resultado);
        verify(tejidoPortOut).updateOut(tejidoId, tejidoActualizado);
    }

    @Test
    void updateIn_whenNombreConflicts_shouldThrowDuplicateResourceException() {
        Tejido tejidoActual = new Tejido();
        tejidoActual.setNombre("Tejido anterior");

        Tejido tejidoActualizado = new Tejido();
        tejidoActualizado.setNombre("Tejido anterior");
        tejidoActualizado.setCategoriasId(List.of(categoriaId));

        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejidoActual);
        when(tejidoPortOut.existsByNombre(tejidoActualizado.getNombre())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> tejidoService.updateIn(tejidoId, tejidoActualizado));

        verify(categoriaPortOut, never()).existsAllById(tejidoActualizado.getCategoriasId());
        verify(tejidoPortOut, never()).updateOut(tejidoId, tejidoActualizado);
    }

    @Test
    void updateIn_whenCategoriasDoNotExist_shouldThrowResourceNotFoundException() {
        Tejido tejidoActual = new Tejido();
        tejidoActual.setNombre("Tejido anterior");

        Tejido tejidoActualizado = new Tejido();
        tejidoActualizado.setNombre("Tejido nuevo");
        tejidoActualizado.setCategoriasId(List.of(categoriaId));

        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejidoActual);
        when(tejidoPortOut.existsByNombre(tejidoActualizado.getNombre())).thenReturn(false);
        when(categoriaPortOut.existsAllById(tejidoActualizado.getCategoriasId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> tejidoService.updateIn(tejidoId, tejidoActualizado));

        verify(tejidoPortOut, never()).updateOut(tejidoId, tejidoActualizado);
    }

    @Test
    void deleteIn_whenTejidoExists_shouldDelete() {
        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(tejido);

        tejidoService.deleteIn(tejidoId);

        verify(tejidoPortOut).deleteOut(tejidoId);
    }

    @Test
    void deleteIn_whenTejidoDoesNotExist_shouldThrowResourceNotFoundException() {
        when(tejidoPortOut.findByIdOut(tejidoId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> tejidoService.deleteIn(tejidoId));

        verify(tejidoPortOut, never()).deleteOut(tejidoId);
    }

    @Test
    void findByCategoriaNombreIn_whenCategoriaExists_shouldReturnTejidos() {
        when(categoriaPortOut.existsByNombre("lana")).thenReturn(true);
        when(tejidoPortOut.findByCategoriaNombreOut("lana")).thenReturn(List.of(tejido));

        List<Tejido> resultado = tejidoService.findByCategoriaNombreIn("lana");

        assertEquals(1, resultado.size());
        assertSame(tejido, resultado.get(0));
        verify(tejidoPortOut).findByCategoriaNombreOut("lana");
    }

    @Test
    void findByCategoriaNombreIn_whenCategoriaDoesNotExist_shouldThrowResourceNotFoundException() {
        when(categoriaPortOut.existsByNombre("lana")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> tejidoService.findByCategoriaNombreIn("lana"));

        verify(tejidoPortOut, never()).findByCategoriaNombreOut("lana");
    }

    @Test
    void findByContieneNombreIn_shouldDelegateToPort() {
        when(tejidoPortOut.findByContieneNombre("tej")).thenReturn(List.of(tejido));

        List<Tejido> resultado = tejidoService.findByContieneNombreIn("tej");

        assertEquals(1, resultado.size());
        assertSame(tejido, resultado.get(0));
        verify(tejidoPortOut).findByContieneNombre("tej");
    }

    @Test
    void findByPrecioMaxIn_shouldDelegateToPort() {
        when(tejidoPortOut.findByPrecioMaxOut(150.0)).thenReturn(List.of(tejido));

        List<Tejido> resultado = tejidoService.findByPrecioMaxIn(150.0);

        assertEquals(1, resultado.size());
        assertSame(tejido, resultado.get(0));
        verify(tejidoPortOut).findByPrecioMaxOut(150.0);
    }
}
