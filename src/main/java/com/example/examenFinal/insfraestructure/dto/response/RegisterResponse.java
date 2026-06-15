package com.example.examenFinal.insfraestructure.dto.response;

import com.example.examenFinal.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {
    private String token;
    UUID id;
    String username;
    String email;
    String rol;
    Date createdAt;

    public static RegisterResponse fromDomain(Usuario usuario, String token) {
        return new RegisterResponse(
                token,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getCreatedAt()
        );
    }
}
