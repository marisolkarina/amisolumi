package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.domain.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioPortOut {
    Usuario createOut(Usuario usuario);
    Usuario findByIdOut(UUID id);
    List<Usuario> findAllOut();
    Usuario updateOut(UUID id, Usuario usuario);
    void deleteOut(UUID id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsRole(String role);
    Usuario findByUsername(String username);
}
