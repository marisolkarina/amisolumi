package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Date createdAt;
    private Date updatedAt;

    public static CategoriaResponse fromDomain(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getCreatedAt(),
                categoria.getUpdatedAt()
        );
    }
}
