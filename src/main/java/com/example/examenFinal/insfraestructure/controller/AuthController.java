package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.in.UsuarioPortIn;
import com.example.examenFinal.insfraestructure.dto.request.LoginRequest;
import com.example.examenFinal.insfraestructure.dto.request.UsuarioRequest;
import com.example.examenFinal.insfraestructure.dto.response.ApiResponse;
import com.example.examenFinal.insfraestructure.dto.response.LoginResponse;
import com.example.examenFinal.insfraestructure.dto.response.RegisterResponse;
import com.example.examenFinal.insfraestructure.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Qualifier("usuarioService")
    private final UsuarioPortIn usuarioPortIn;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    public AuthController(UsuarioPortIn usuarioPortIn, AuthenticationManager manager, JwtService jwtService) {
        this.usuarioPortIn = usuarioPortIn;
        this.manager = manager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        // se  registra el usuario
        Usuario usuariocreado = usuarioPortIn.createIn(usuarioRequest.toDomain());
        // generamos el token
        String username = usuarioRequest.getUsername();
        String rol = usuarioPortIn.findByUsername(username).getRol();
        String token = jwtService.generateToken(username, rol);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<RegisterResponse>(
                        true,
                        "Registro exitoso",
                        RegisterResponse.fromDomain(usuariocreado, token))
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        // si falla se lanza bad credentials
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        String username = loginRequest.getUsername();
        Usuario usuarioLogueado = usuarioPortIn.findByUsername(username);
        String token = jwtService.generateToken(username, usuarioLogueado.getRol());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<LoginResponse>(
                        true,
                        "Inicio de sesion exitoso",
                        LoginResponse.fromDomain(usuarioLogueado,token)
                ));

    }

}
