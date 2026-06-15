package com.example.examenFinal.application;

import com.example.examenFinal.domain.model.Categoria;
import com.example.examenFinal.domain.ports.in.CategoriaPortIn;
import com.example.examenFinal.domain.ports.out.CategoriaPortOut;
import com.example.examenFinal.insfraestructure.exception.DuplicateResourceException;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService implements CategoriaPortIn {

    private final CategoriaPortOut categoriaPortOut;

    public CategoriaService(CategoriaPortOut categoriaPortOut) {
        this.categoriaPortOut = categoriaPortOut;
    }

    @Override
    public Categoria createIn(Categoria categoria) {
        String categoriaBuscada = categoria.getNombre();
        if (categoriaPortOut.existsByNombre(categoriaBuscada)) {
            throw new DuplicateResourceException("Ya existe una categoria registrada con ese nombre");
        }
        return categoriaPortOut.createOut(categoria);
    }

    @Override
    public Categoria findByIdIn(UUID id) {
        return categoriaPortOut.findByIdOut(id);
    }

    @Override
    public List<Categoria> findAllIn() {
        return categoriaPortOut.findAllOut();
    }

    @Override
    public Categoria updateIn(UUID id, Categoria categoria) {
        Categoria categoriaBuscada = categoriaPortOut.findByIdOut(id);

        boolean nombreConflict = categoriaPortOut.existsByNombre(categoria.getNombre())
                && !categoriaBuscada.getNombre().equalsIgnoreCase(categoria.getNombre());
        if (nombreConflict) throw new DuplicateResourceException("Ya existe una categoria con ese nombre.");

        return categoriaPortOut.updateOut(id, categoria);
    }

    @Override
    public void deleteIn(UUID id) {
        Categoria categoriaBuscada = categoriaPortOut.findByIdOut(id);
        if (categoriaBuscada==null) {
            throw new ResourceNotFoundException("Categoria con id "+id+" no existe");
        }
        categoriaPortOut.deleteOut(id);
    }


}
