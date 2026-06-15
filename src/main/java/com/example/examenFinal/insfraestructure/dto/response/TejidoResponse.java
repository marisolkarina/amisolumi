package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Tejido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TejidoResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private Integer tiempoProduccion;
    private Date createdAt;
    private Date updatedAt;
    private UUID usuarioId;
    private List<UUID> categoriasId;

    public static TejidoResponse fromDomain(Tejido tejido) {
        return new TejidoResponse(
                tejido.getId(),
                tejido.getNombre(),
                tejido.getDescripcion(),
                tejido.getPrecio(),
                tejido.getImagen(),
                tejido.getTiempoProduccion(),
                tejido.getCreatedAt(),
                tejido.getUpdatedAt(),
                tejido.getUsuarioId(),
                tejido.getCategoriasId()
        );
    }
}
