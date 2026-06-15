package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public Categoria toDomain() {
        return new Categoria(
                null,
                nombre,
                descripcion,
                null,
                null,
                null
        );
    }
}
