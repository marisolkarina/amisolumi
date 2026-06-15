package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponse {
    private UUID id;
    private String nombres;
    private String apellidos;
    private String email;
    private String celular;
    private String direccion;
    private String dni;
    private String username;
    private Date fechaNacimiento;
    private String rol;
    private Date createdAt;
    private Date updatedAt;

    public static UsuarioResponse fromDomain(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getDireccion(),
                usuario.getDni(),
                usuario.getUsername(),
                usuario.getFechaNacimiento(),
                usuario.getRol(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
