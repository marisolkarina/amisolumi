package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.model.Categoria;
import com.example.examenFinal.domain.ports.out.CategoriaPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import com.example.examenFinal.insfraestructure.mapper.CategoriaMapper;
import com.example.examenFinal.insfraestructure.persistance.entity.CategoriaEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.CategoriaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategoriaAdapter implements CategoriaPortOut {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaAdapter(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public Categoria createOut(Categoria categoria) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre(categoria.getNombre());
        categoriaEntity.setDescripcion(categoria.getDescripcion());
        categoriaEntity.setTejidos(new ArrayList<>());

        //guardo en bd
        categoriaRepository.save(categoriaEntity);

        //asigno al modelo el id generado y campos de auditoria
        categoria.setId(categoriaEntity.getId());
        categoria.setCreatedAt(categoriaEntity.getCreatedAt());
        categoria.setUpdatedAt(categoriaEntity.getUpdatedAt());
        categoria.setTejidos(new ArrayList<>());

        return categoria;
    }

    @Override
    public Categoria findByIdOut(UUID id) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id "+id+" no existe"));

        return categoriaMapper.entityToModel(categoriaEntity);
    }

    @Override
    public List<Categoria> findAllOut() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::entityToModel)
                .toList();
    }

    @Override
    public Categoria updateOut(UUID id, Categoria categoria) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(id).orElseThrow();

        // solo cambia el nombre
        categoriaMapper.modelToEntityUpd(categoria, categoriaEntity);

        // se guarda en bd
        CategoriaEntity categoriaUpd = categoriaRepository.save(categoriaEntity);
        return categoriaMapper.entityToModel(categoriaUpd);
    }

    @Override
    public void deleteOut(UUID id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.findByNombreIgnoreCase(nombre).isPresent();
    }

    @Override
    public boolean existsAllById(List<UUID> categoriasId) {
        List<CategoriaEntity> categorias =
                categoriaRepository.findAllById(categoriasId);
        return categorias.size() == categoriasId.size();
    }
}
