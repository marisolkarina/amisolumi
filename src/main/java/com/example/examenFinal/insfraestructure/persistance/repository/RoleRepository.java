package com.example.examenFinal.insfraestructure.persistance.repository;

import com.example.examenFinal.insfraestructure.persistance.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String roleName);
}

