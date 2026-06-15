package com.example.examenFinal.insfraestructure.mapper;

import com.example.examenFinal.domain.model.Categoria;
import com.example.examenFinal.insfraestructure.persistance.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoriaMapper {
    public Categoria entityToModel(CategoriaEntity categoriaEntity) {
        return new Categoria(
                categoriaEntity.getId(),
                categoriaEntity.getNombre(),
                categoriaEntity.getDescripcion(),
                categoriaEntity.getCreatedAt(),
                categoriaEntity.getUpdatedAt(),
                new ArrayList<>()
        );
    }

    public void modelToEntityUpd(Categoria categoriaModel, CategoriaEntity categoriaEntity) {
        categoriaEntity.setNombre(categoriaModel.getNombre());
        categoriaEntity.setDescripcion(categoriaModel.getDescripcion());
    }
}
