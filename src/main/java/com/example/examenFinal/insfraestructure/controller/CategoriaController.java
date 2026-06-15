package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.model.Categoria;
import com.example.examenFinal.domain.ports.in.CategoriaPortIn;
import com.example.examenFinal.insfraestructure.dto.request.CategoriaRequest;
import com.example.examenFinal.insfraestructure.dto.response.ApiResponse;
import com.example.examenFinal.insfraestructure.dto.response.CategoriaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    @Qualifier("usuarioService")
    private final CategoriaPortIn categoriaPortIn;

    public CategoriaController(CategoriaPortIn categoriaPortIn) {
        this.categoriaPortIn = categoriaPortIn;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponse>> create(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        Categoria categoriaCreada = categoriaPortIn.createIn(categoriaRequest.toDomain());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<CategoriaResponse>(
                        true,
                        "Categoría creada con éxito",
                        CategoriaResponse.fromDomain(categoriaCreada))
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> findById(@PathVariable UUID id) {
        Categoria categoria = categoriaPortIn.findByIdIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<CategoriaResponse>(
                        true,
                        "Categoria encontrada",
                        CategoriaResponse.fromDomain(categoria)
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<List<CategoriaResponse>>(
                        true,
                        "Categorias encontradas",
                        categoriaPortIn.findAllIn()
                                .stream()
                                .map(CategoriaResponse::fromDomain)
                                .toList()
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoriaRequest categoria) {

        Categoria categoriaUpd = categoriaPortIn.updateIn(id, categoria.toDomain());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<CategoriaResponse>(
                        true,
                        "Categoria actualizada",
                        CategoriaResponse.fromDomain(categoriaUpd)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        categoriaPortIn.deleteIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<String>(
                        true,
                        "Categoria eliminada",
                        "id: "+id
                ));
    }
}
