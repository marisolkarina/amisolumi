package com.example.examenFinal.insfraestructure.mapper;

import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.insfraestructure.persistance.entity.RolEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario entityToModel(UsuarioEntity usuarioEntity) {
        return new Usuario(
                usuarioEntity.getId(),
                usuarioEntity.getNombres(),
                usuarioEntity.getApellidos(),
                usuarioEntity.getEmail(),
                usuarioEntity.getPassword(), // encriptado
                usuarioEntity.getCelular(),
                usuarioEntity.getDireccion(),
                usuarioEntity.getDni(),
                usuarioEntity.getUsername(),
                usuarioEntity.getFechaNacimiento(),
                usuarioEntity.getRol().getNombre(),
                usuarioEntity.getCreatedAt(),
                usuarioEntity.getUpdatedAt()
        );
    }

    public void modelToEntityUpd(Usuario usuarioModel, UsuarioEntity usuarioEntity) {
        usuarioEntity.setEmail(usuarioModel.getEmail());
        usuarioEntity.setCelular(usuarioModel.getCelular());
        usuarioEntity.setDireccion(usuarioModel.getDireccion());
        usuarioEntity.setUsername(usuarioModel.getUsername());
        usuarioEntity.setFechaNacimiento(usuarioModel.getFechaNacimiento());
    }
}
