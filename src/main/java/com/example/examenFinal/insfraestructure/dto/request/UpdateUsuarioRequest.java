package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateUsuarioRequest {
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato valido")
    private String email;
    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El celular debe contener solo numeros")
    @Size(min = 9, max = 9, message = "El celular debe tener longitud de 9")
    private String celular;
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;
    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date fechaNacimiento;
    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    public Usuario toDomain() {
        return new Usuario(
                null,
                null,
                null,
                email,
                null,
                celular,
                direccion,
                null,
                username,
                fechaNacimiento,
                rol,
                null, null
        );
    }
}
