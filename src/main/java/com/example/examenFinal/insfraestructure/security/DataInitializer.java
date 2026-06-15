package com.example.examenFinal.insfraestructure.security;

import com.example.examenFinal.insfraestructure.persistance.entity.RolEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.Role;
import com.example.examenFinal.insfraestructure.persistance.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (Role role : Role.values()) {
            RolEntity roleEntity = roleRepository.findByNombre(role.name()).orElse(null);
            if (roleEntity==null) {
                RolEntity roleCreated = new RolEntity();
                roleCreated.setNombre(role.name());
                roleRepository.save(roleCreated);
            }
        }
    }
}
