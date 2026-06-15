package com.example.examenFinal.insfraestructure.dto.request;

import com.example.examenFinal.domain.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioRequest {
//    @NotBlank(message = "El nombre es obligatorio")
//    private String nombres;
//    @NotBlank(message = "El apellido es obligatorio")
//    private String apellidos;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato valido")
    private String email;
    @Pattern(regexp = "^\\d+$", message = "El password debe contener solo numeros")
    @Size(min = 6, message = "El password debe tener longitud minima de 6")
    private String password;
    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El celular debe contener solo numeros")
    @Size(min = 9, max = 9, message = "El celular debe tener longitud de 9")
    private String celular;
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener longitud de 8")
    private String dni;
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
                password,
                celular,
                direccion,
                dni,
                username,
                fechaNacimiento,
                rol,
                null, null
        );
    }
}
