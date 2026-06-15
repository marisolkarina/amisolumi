package com.example.examenFinal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private UUID id;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String celular;
    private String direccion;
    private String dni;
    private String username;
    private Date fechaNacimiento;
    private String rol;
    private Date createdAt;
    private Date updatedAt;
}
