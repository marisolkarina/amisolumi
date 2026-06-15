package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    UUID id;
    String username;
    String email;
    String rol;

    public static LoginResponse fromDomain(Usuario usuario, String token) {
        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}
