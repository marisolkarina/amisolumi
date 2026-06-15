package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.model.Tejido;
import com.example.examenFinal.domain.ports.in.TejidoPortIn;
import com.example.examenFinal.insfraestructure.dto.request.TejidoRequest;
import com.example.examenFinal.insfraestructure.dto.request.UpdateTejidoRequest;
import com.example.examenFinal.insfraestructure.dto.response.ApiResponse;
import com.example.examenFinal.insfraestructure.dto.response.TejidoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tejidos")
public class TejidoController {
    @Qualifier("tejidoService")
    private final TejidoPortIn tejidoPortIn;

    public TejidoController(TejidoPortIn tejidoPortIn) {
        this.tejidoPortIn = tejidoPortIn;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TejidoResponse>> create(@Valid @RequestBody TejidoRequest tejidoRequest) {
        Tejido tejidoCreado = tejidoPortIn.createIn(tejidoRequest.toDomain());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<TejidoResponse>(
                        true,
                        "Tejido creado con éxito",
                        TejidoResponse.fromDomain(tejidoCreado))
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TejidoResponse>> findById(@PathVariable UUID id) {

        Tejido tejido = tejidoPortIn.findByIdIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<TejidoResponse>(
                        true,
                        "Tejido encontrado",
                        TejidoResponse.fromDomain(tejido)
                ));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<TejidoResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<List<TejidoResponse>>(
                        true,
                        "Tejidos encontrados",
                        tejidoPortIn.findAllIn()
                                .stream()
                                .map(TejidoResponse::fromDomain)
                                .toList()
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TejidoResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTejidoRequest tejido) {

        Tejido tejidoUpd = tejidoPortIn.updateIn(id, tejido.toDomain());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<TejidoResponse>(
                        true,
                        "Tejido actualizado",
                        TejidoResponse.fromDomain(tejidoUpd)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        tejidoPortIn.deleteIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<String>(
                        true,
                        "Tejido eliminado",
                        "id: "+id
                ));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<List<TejidoResponse>>> findByTejidoNombre(
            @PathVariable String nombre) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Tejidos encontrados",
                        tejidoPortIn.findByContieneNombreIn(nombre)
                                .stream()
                                .map(TejidoResponse::fromDomain)
                                .toList()
                )
        );
    }

    @GetMapping("/categoria/{nombre}")
    public ResponseEntity<ApiResponse<List<TejidoResponse>>> findByCategoria(
            @PathVariable String nombre) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Tejidos encontrados",
                        tejidoPortIn.findByCategoriaNombreIn(nombre)
                                .stream()
                                .map(TejidoResponse::fromDomain)
                                .toList()
                )
        );
    }

    @GetMapping("/precioMax/{precioMax}")
    public ResponseEntity<ApiResponse<List<TejidoResponse>>> findByPrecioMax(
            @PathVariable Double precioMax) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Tejidos encontrados",
                        tejidoPortIn.findByPrecioMaxIn(precioMax)
                                .stream()
                                .map(TejidoResponse::fromDomain)
                                .toList()
                )
        );
    }

}
