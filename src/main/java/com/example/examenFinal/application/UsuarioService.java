package com.example.examenFinal.application;

import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.in.UsuarioPortIn;
import com.example.examenFinal.domain.ports.out.ReniecPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.dto.response.ReniecResponse;
import com.example.examenFinal.insfraestructure.exception.DuplicateResourceException;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService implements UsuarioPortIn {

    private final UsuarioPortOut usuarioPortOut;
    private final ReniecPortOut reniecPortOut;

    public UsuarioService(UsuarioPortOut usuarioPortOut, ReniecPortOut reniecPortOut) {
        this.usuarioPortOut = usuarioPortOut;
        this.reniecPortOut = reniecPortOut;
    }

    @Override
    public Usuario createIn(Usuario usuario) {

        ReniecResponse persona = reniecPortOut.getPersonaInfo(usuario.getDni());
        usuario.setNombres(persona.getFirstName());
        usuario.setApellidos(persona.getFirstLastName()+" "+persona.getSecondLastName());

        String usernameBuscado = usuario.getUsername();
        if (usuarioPortOut.existsByUsername(usernameBuscado)) {
            throw new DuplicateResourceException("Ya existe un usuario registrado con ese username");
        }
        if (usuarioPortOut.existsByEmail(usuario.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario registrado con ese email");
        }

        if (!usuarioPortOut.existsRole(usuario.getRol())) {
            throw new ResourceNotFoundException("Rol no encontrado: "+usuario.getRol());
        }
        return usuarioPortOut.createOut(usuario);
    }

    @Override
    public Usuario findByIdIn(UUID id) {
        return usuarioPortOut.findByIdOut(id);
    }

    @Override
    public List<Usuario> findAllIn() {
        return usuarioPortOut.findAllOut();
    }

    @Override
    public Usuario updateIn(UUID id, Usuario usuario) {
        Usuario usuarioBuscado = usuarioPortOut.findByIdOut(id);

        boolean emailConflict = usuarioPortOut.existsByEmail(usuario.getEmail())
                && !usuarioBuscado.getEmail().equalsIgnoreCase(usuario.getEmail());
        if (emailConflict) throw new DuplicateResourceException("Ya existe un usuario con ese email.");

        boolean usernameConflict = usuarioPortOut.existsByUsername(usuario.getUsername())
                        && !usuarioBuscado.getUsername().equalsIgnoreCase(usuario.getUsername());
        if (usernameConflict) throw new DuplicateResourceException("Ya existe un usuario con ese username");

        return usuarioPortOut.updateOut(id, usuario);
    }

    @Override
    public void deleteIn(UUID id) {
        Usuario usuarioBuscado = usuarioPortOut.findByIdOut(id);
        if (usuarioBuscado==null) {
            throw new ResourceNotFoundException("Usuario con id "+id+" no existe");
        }
        usuarioPortOut.deleteOut(id);
    }

    @Override
    public Usuario findByUsername(String username) { return usuarioPortOut.findByUsername(username); }

}
