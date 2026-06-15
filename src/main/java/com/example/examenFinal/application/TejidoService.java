package com.example.examenFinal.application;

import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.ports.in.TejidoPortIn;
import com.example.examenFinal.domain.ports.out.CategoriaPortOut;
import com.example.examenFinal.domain.ports.out.TejidoPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.exception.DuplicateResourceException;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TejidoService implements TejidoPortIn {

    private final TejidoPortOut tejidoPortOut;
    private final UsuarioPortOut usuarioPortOut;
    private final CategoriaPortOut categoriaPortOut;

    public TejidoService(TejidoPortOut tejidoPortOut, UsuarioPortOut usuarioPortOut, CategoriaPortOut categoriaPortOut) {
        this.tejidoPortOut = tejidoPortOut;
        this.usuarioPortOut = usuarioPortOut;
        this.categoriaPortOut = categoriaPortOut;
    }

    @Override
    public Tejido createIn(Tejido tejido) {
        String nombreBuscado = tejido.getNombre();
        if (tejidoPortOut.existsByNombre(nombreBuscado)) {
            throw new DuplicateResourceException("Ya existe un tejido registrado con ese nombre");
        }

        if (usuarioPortOut.findByIdOut(tejido.getUsuarioId())==null) {
            throw new ResourceNotFoundException("Usuario no existe");
        }

        if (!categoriaPortOut.existsAllById(tejido.getCategoriasId())) {
            throw new ResourceNotFoundException("Una o más categorías no existen");
        }

        return tejidoPortOut.createOut(tejido);
    }

    @Override
    public Tejido findByIdIn(UUID id) {
        return tejidoPortOut.findByIdOut(id);
    }

    @Override
    public List<Tejido> findAllIn() {
        return tejidoPortOut.findAllOut();
    }

    @Override
    public Tejido updateIn(UUID id, Tejido tejido) {
        Tejido tejidoBuscado = findByIdIn(id);

        boolean nombreConflict = tejidoPortOut.existsByNombre(tejido.getNombre())
                && !tejidoBuscado.getNombre().equalsIgnoreCase(tejido.getNombre());
        if (nombreConflict) throw new DuplicateResourceException("Ya existe un tejido con ese nombre");

        System.out.println("nombre de tejido update ok");

        boolean categoriasExisten = categoriaPortOut.existsAllById(tejido.getCategoriasId());
        if (!categoriasExisten) throw new ResourceNotFoundException("Una o más categorías no existen");

        System.out.println("categorias update ok");

        return tejidoPortOut.updateOut(id, tejido);
    }

    @Override
    public void deleteIn(UUID id) {
        Tejido tejidoBuscado = tejidoPortOut.findByIdOut(id);
        if (tejidoBuscado==null) {
            throw new ResourceNotFoundException("Tejido con id "+id+" no existe");
        }
        tejidoPortOut.deleteOut(id);
    }

    public List<Tejido> findByCategoriaNombreIn(String nombreCategoria) {

        if (!categoriaPortOut.existsByNombre(nombreCategoria)) {
            throw new ResourceNotFoundException(
                    "Categoría no encontrada: " + nombreCategoria
            );
        }

        return tejidoPortOut.findByCategoriaNombreOut(nombreCategoria);
    }

    @Override
    public List<Tejido> findByContieneNombreIn(String nombre) {
        return tejidoPortOut.findByContieneNombre(nombre);
    }

    @Override
    public List<Tejido> findByPrecioMaxIn(Double precioMax) {
        return tejidoPortOut.findByPrecioMaxOut(precioMax);
    }

}
