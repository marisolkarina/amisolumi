package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import com.example.examenFinal.insfraestructure.mapper.UsuarioMapper;
import com.example.examenFinal.insfraestructure.persistance.entity.RolEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.RoleRepository;
import com.example.examenFinal.insfraestructure.persistance.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UsuarioAdapter implements UsuarioPortOut {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioAdapter(UsuarioRepository usuarioRepository, RoleRepository roleRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario createOut(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setNombres(usuario.getNombres());
        usuarioEntity.setApellidos(usuario.getApellidos());
        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuarioEntity.setCelular(usuario.getCelular());
        usuarioEntity.setDireccion(usuario.getDireccion());
        usuarioEntity.setDni(usuario.getDni());
        usuarioEntity.setUsername(usuario.getUsername());
        usuarioEntity.setFechaNacimiento(usuario.getFechaNacimiento());
        RolEntity rolIngresado = roleRepository.findByNombre(usuario.getRol()).orElse(null);
        usuarioEntity.setRol(rolIngresado);

        // se guarda en bd
        usuarioRepository.save(usuarioEntity);
        // se asigna el id generado al modelo de usuario y campos de auditoria
        usuario.setId(usuarioEntity.getId());
        usuario.setCreatedAt(usuarioEntity.getCreatedAt());
        usuario.setUpdatedAt(usuarioEntity.getUpdatedAt());

        return usuario;
    }

    @Override
    public Usuario findByIdOut(UUID id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id "+id+" no existe"));

        return usuarioMapper.entityToModel(usuarioEntity);
    }

    @Override
    public List<Usuario> findAllOut() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::entityToModel)
                .toList();
    }

    @Override
    public Usuario updateOut(UUID id, Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow();

        // dni, nombres, apellidos, password no se modifican
        usuarioMapper.modelToEntityUpd(usuario, usuarioEntity);
        // setear rol
        RolEntity rolIngresado = roleRepository
                .findByNombre(usuario.getRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        usuarioEntity.setRol(rolIngresado);

        // se guarda en bd
        UsuarioEntity usuarioUpd = usuarioRepository.save(usuarioEntity);

        return usuarioMapper.entityToModel(usuarioUpd);
    }

    @Override
    public void deleteOut(UUID id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email).isPresent();
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.findByUsernameIgnoreCase(username).isPresent();
    }

    @Override
    public boolean existsRole(String role) {
        return roleRepository.findByNombre(role).isPresent();
    }

    public Usuario findByUsername(String username) { UsuarioEntity usuarioEntity = usuarioRepository.findByUsernameIgnoreCase(username).orElse(null); if (usuarioEntity==null) return null; return usuarioMapper.entityToModel(usuarioEntity); }


}
