package com.example.examenFinal.insfraestructure.security;

import com.example.examenFinal.insfraestructure.persistance.entity.RolEntity;
import com.example.examenFinal.insfraestructure.persistance.entity.UsuarioEntity;
import com.example.examenFinal.insfraestructure.persistance.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByUsernameIgnoreCase(username);
        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario "+username+" no encontrado: ");
        }

        UsuarioEntity usuarioEntity =  usuarioOptional.get();
        RolEntity role = usuarioEntity.getRol();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getNombre()));
        return new User(usuarioEntity.getUsername(), usuarioEntity.getPassword(), authorities);
    }
}
