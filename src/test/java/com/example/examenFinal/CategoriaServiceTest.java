package com.example.examenFinal;

import com.example.examenFinal.application.CategoriaService;
import com.example.examenFinal.domain.model.Categoria;
import com.example.examenFinal.domain.ports.out.CategoriaPortOut;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaPortOut categoriaPortOut;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private Categoria categoriaActual;
    private UUID categoriaId;

    @BeforeEach
    void setUp() {
        categoriaId = UUID.fromString("123e4567-e89b-12d3-a456-426614174500");

        categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNombre("Lana");
        categoria.setDescripcion("Categoria de lana");
        categoria.setCreatedAt(new Date());
        categoria.setUpdatedAt(new Date());

        categoriaActual = new Categoria();
        categoriaActual.setId(categoriaId);
        categoriaActual.setNombre("Lana");
        categoriaActual.setDescripcion("Categoria actual");
        categoriaActual.setCreatedAt(new Date());
        categoriaActual.setUpdatedAt(new Date());
    }

    @Test
    void createIn_whenNombreIsAvailable_shouldCreateCategoria() {
        when(categoriaPortOut.existsByNombre(categoria.getNombre())).thenReturn(false);
        when(categoriaPortOut.createOut(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaService.createIn(categoria);

        assertSame(categoria, resultado);
        verify(categoriaPortOut).createOut(categoria);
    }

    @Test
    void createIn_whenNombreExists_shouldThrowDuplicateResourceException() {
        when(categoriaPortOut.existsByNombre(categoria.getNombre())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoriaService.createIn(categoria));

        verify(categoriaPortOut, never()).createOut(categoria);
    }

    @Test
    void findByIdIn_shouldDelegateToPort() {
        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(categoria);

        Categoria resultado = categoriaService.findByIdIn(categoriaId);

        assertSame(categoria, resultado);
        verify(categoriaPortOut).findByIdOut(categoriaId);
    }

    @Test
    void findAllIn_shouldDelegateToPort() {
        when(categoriaPortOut.findAllOut()).thenReturn(List.of(categoria));

        List<Categoria> resultado = categoriaService.findAllIn();

        assertEquals(1, resultado.size());
        assertSame(categoria, resultado.get(0));
        verify(categoriaPortOut).findAllOut();
    }

    @Test
    void updateIn_whenNombreDoesNotConflict_shouldUpdateCategoria() {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Algodon");
        categoriaActualizada.setDescripcion("Categoria actualizada");

        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(categoriaActual);
        when(categoriaPortOut.existsByNombre(categoriaActualizada.getNombre())).thenReturn(false);
        when(categoriaPortOut.updateOut(categoriaId, categoriaActualizada)).thenReturn(categoriaActualizada);

        Categoria resultado = categoriaService.updateIn(categoriaId, categoriaActualizada);

        assertSame(categoriaActualizada, resultado);
        verify(categoriaPortOut).updateOut(categoriaId, categoriaActualizada);
    }

    @Test
    void updateIn_whenNombreConflicts_shouldThrowDuplicateResourceException() {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Algodon");
        categoriaActualizada.setDescripcion("Categoria actualizada");

        Categoria categoriaBuscada = new Categoria();
        categoriaBuscada.setNombre("Lana");

        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(categoriaBuscada);
        when(categoriaPortOut.existsByNombre(categoriaActualizada.getNombre())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoriaService.updateIn(categoriaId, categoriaActualizada));

        verify(categoriaPortOut, never()).updateOut(categoriaId, categoriaActualizada);
    }

    @Test
    void updateIn_whenSameNombreShouldAllowUpdate() {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Lana");
        categoriaActualizada.setDescripcion("Categoria actualizada");

        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(categoriaActual);
        when(categoriaPortOut.existsByNombre(categoriaActualizada.getNombre())).thenReturn(true);
        when(categoriaPortOut.updateOut(categoriaId, categoriaActualizada)).thenReturn(categoriaActualizada);

        Categoria resultado = categoriaService.updateIn(categoriaId, categoriaActualizada);

        assertSame(categoriaActualizada, resultado);
        verify(categoriaPortOut).updateOut(categoriaId, categoriaActualizada);
    }

    @Test
    void deleteIn_whenCategoriaExists_shouldDelete() {
        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(categoriaActual);

        categoriaService.deleteIn(categoriaId);

        verify(categoriaPortOut).deleteOut(categoriaId);
    }

    @Test
    void deleteIn_whenCategoriaDoesNotExist_shouldThrowResourceNotFoundException() {
        when(categoriaPortOut.findByIdOut(categoriaId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.deleteIn(categoriaId));

        verify(categoriaPortOut, never()).deleteOut(categoriaId);
    }

}
