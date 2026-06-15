package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.in.UsuarioPortIn;
import com.example.examenFinal.insfraestructure.dto.request.UpdateUsuarioRequest;
import com.example.examenFinal.insfraestructure.dto.request.UsuarioRequest;
import com.example.examenFinal.insfraestructure.dto.response.ApiResponse;
import com.example.examenFinal.insfraestructure.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {
    @Qualifier("usuarioService")
    private final UsuarioPortIn usuarioPortIn;

    public UsuarioController(UsuarioPortIn usuarioPortIn) {
        this.usuarioPortIn = usuarioPortIn;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> findById(@PathVariable UUID id) {
        Usuario usuario = usuarioPortIn.findByIdIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<UsuarioResponse>(
                        true,
                        "Usuario encontrado",
                        UsuarioResponse.fromDomain(usuario)
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<List<UsuarioResponse>>(
                        true,
                        "Usuarios encontrados",
                        usuarioPortIn.findAllIn()
                                .stream()
                                .map(UsuarioResponse::fromDomain)
                                .toList()
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUsuarioRequest usuario) {

        Usuario usuarioUpd = usuarioPortIn.updateIn(id, usuario.toDomain());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<UsuarioResponse>(
                        true,
                        "Usuario actualizado",
                        UsuarioResponse.fromDomain(usuarioUpd)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        usuarioPortIn.deleteIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<String>(
                        true,
                        "Usuario eliminado",
                        "id: "+id
                ));
    }
}
