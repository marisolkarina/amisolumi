package com.example.examenFinal.domain.ports.in;

import com.example.examenFinal.domain.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioPortIn {
    Usuario createIn(Usuario usuario);
    Usuario findByIdIn(UUID id);
    List<Usuario> findAllIn();
    Usuario updateIn(UUID id, Usuario usuario);
    void deleteIn(UUID id);
    Usuario findByUsername(String username);
}
