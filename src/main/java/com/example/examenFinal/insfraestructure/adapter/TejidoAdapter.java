package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.ports.out.TejidoPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import com.example.examenFinal.insfraestructure.mapper.TejidoMapper;
import com.example.examenFinal.insfraestructure.persistance.entity.CategoriaEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.TejidoEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.CategoriaRepository;
import com.example.examenFinal.insfraestructure.persistance.repository.TejidoRepository;
import com.example.examenFinal.insfraestructure.persistance.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TejidoAdapter implements TejidoPortOut {

    private final TejidoRepository tejidoRepository;
    private final TejidoMapper tejidoMapper;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public TejidoAdapter(TejidoRepository tejidoRepository, TejidoMapper tejidoMapper, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
        this.tejidoRepository = tejidoRepository;
        this.tejidoMapper = tejidoMapper;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Tejido createOut(Tejido tejido) {
        TejidoEntity tejidoEntity = new TejidoEntity();
        tejidoEntity.setNombre(tejido.getNombre());
        tejidoEntity.setDescripcion(tejido.getDescripcion());
        tejidoEntity.setPrecio(tejido.getPrecio());
        tejidoEntity.setImagen(tejido.getImagen());
        tejidoEntity.setTiempoProduccion(tejido.getTiempoProduccion());

        UsuarioEntity usuarioE = usuarioRepository.findById(tejido.getUsuarioId()).orElseThrow();
        tejidoEntity.setUsuario(usuarioE);

        List<CategoriaEntity> categoriasE = categoriaRepository.findAllById(tejido.getCategoriasId());
        tejidoEntity.setCategorias(categoriasE);

        tejidoRepository.save(tejidoEntity);

        tejido.setId(tejidoEntity.getId());
        tejido.setCreatedAt(tejidoEntity.getCreatedAt());
        tejido.setUpdatedAt(tejidoEntity.getUpdatedAt());

        return tejido;
    }

    @Override
    public Tejido findByIdOut(UUID id) {
        TejidoEntity tejidoEntity = tejidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tejido con id "+id+" no existe"));
        return tejidoMapper.entityToModel(tejidoEntity);
    }

    @Override
    public List<Tejido> findAllOut() {
        return tejidoRepository.findAll()
                .stream()
                .map(tejidoMapper::entityToModel)
                .toList();
    }

    @Override
    public Tejido updateOut(UUID id, Tejido tejido) {
        TejidoEntity tejidoEntity = tejidoRepository.findById(id).orElseThrow();

        tejidoMapper.modelToEntityUpd(tejido, tejidoEntity);
        List<CategoriaEntity> categorias = categoriaRepository.findAllById(tejido.getCategoriasId());
        tejidoEntity.setCategorias(categorias);

        // se guarda en bd
        TejidoEntity tejidoUpd = tejidoRepository.save(tejidoEntity);

        return tejidoMapper.entityToModel(tejidoUpd);
    }

    @Override
    public void deleteOut(UUID id) {
        tejidoRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return tejidoRepository.findByNombreIgnoreCase(nombre).isPresent();
    }

    @Override
    public List<Tejido> findByCategoriaNombreOut(String nombre) {

        return tejidoRepository.findByCategorias_NombreIgnoreCase(nombre)
                .stream()
                .map(tejidoMapper::entityToModel)
                .toList();
    }

    @Override
    public List<Tejido> findByContieneNombre(String nombre) {
        return tejidoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(tejidoMapper::entityToModel)
                .toList();
    }

    @Override
    public List<Tejido> findByPrecioMaxOut(Double precioMax) {
        return tejidoRepository.findByPrecioLessThanEqual(precioMax)
                .stream()
                .map(tejidoMapper::entityToModel)
                .toList();
    }
}
