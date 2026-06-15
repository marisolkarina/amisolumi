package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByUsernameIgnoreCase(String username);
    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);
}
