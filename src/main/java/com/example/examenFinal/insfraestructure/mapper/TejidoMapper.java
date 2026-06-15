package com.example.examenFinal.insfraestructure.mapper;

import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.insfraestructure.persistance.entity.CategoriaEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.TejidoEntity;
import org.springframework.stereotype.Component;


@Component
public class TejidoMapper {
    public Tejido entityToModel(TejidoEntity tejidoEntity) {
        return new Tejido(
                tejidoEntity.getId(),
                tejidoEntity.getNombre(),
                tejidoEntity.getDescripcion(),
                tejidoEntity.getPrecio(),
                tejidoEntity.getImagen(),
                tejidoEntity.getTiempoProduccion(),
                tejidoEntity.getCreatedAt(),
                tejidoEntity.getUpdatedAt(),
                tejidoEntity.getUsuario().getId(),

                tejidoEntity.getCategorias()
                        .stream()
                        .map(CategoriaEntity::getId)
                        .toList()
        );
    }

    public void modelToEntityUpd(Tejido tejidoModel, TejidoEntity tejidoEntity) {
        tejidoEntity.setNombre(tejidoModel.getNombre());
        tejidoEntity.setDescripcion(tejidoModel.getDescripcion());
        tejidoEntity.setPrecio(tejidoModel.getPrecio());
        tejidoEntity.setImagen(tejidoModel.getImagen());
        tejidoEntity.setTiempoProduccion(tejidoModel.getTiempoProduccion());
    }
}
