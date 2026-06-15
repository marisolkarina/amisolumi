package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.Tejido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateTejidoRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;
    @NotBlank(message = "La imagen es obligatoria")
    @URL(message = "La imagen no tiene formato de url")
    private String imagen;
    @NotNull(message = "El tiempo de produccion es obligatorio")
    @Positive(message = "El tiempo de produccion debe ser mayor que 0")
    private Integer tiempoProduccion;

    private List<UUID> categoriasId;

    public Tejido toDomain() {
        return new Tejido(
                null,
                nombre,
                descripcion,
                precio,
                imagen,
                tiempoProduccion,
                null,
                null,
                null,
                categoriasId

        );
    }
}
