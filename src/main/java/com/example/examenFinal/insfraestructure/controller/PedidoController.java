package com.example.examenFinal.insfraestructure.controller;

import com.example.examenFinal.domain.model.Pedido;
import com.example.examenFinal.domain.ports.in.PedidoPortIn;
import com.example.examenFinal.insfraestructure.dto.request.DetallePedidoRequest;
import com.example.examenFinal.insfraestructure.dto.request.PedidoRequest;
import com.example.examenFinal.insfraestructure.dto.request.UpdateCantidad;
import com.example.examenFinal.insfraestructure.dto.response.ApiResponse;
import com.example.examenFinal.insfraestructure.dto.response.PedidoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Qualifier("pedidoService")
    private final PedidoPortIn pedidoPortIn;

    public PedidoController(PedidoPortIn pedidoPortIn) {
        this.pedidoPortIn = pedidoPortIn;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> create(@Valid @RequestBody PedidoRequest pedidoRequest) {
        Pedido pedidoCreado = pedidoPortIn.createIn(pedidoRequest.getUsuarioId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<PedidoResponse>(
                        true,
                        "Pedido inicializado con éxito",
                        PedidoResponse.fromDomain(pedidoCreado))
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> findById(@PathVariable UUID id) {
        Pedido pedido = pedidoPortIn.findByIdIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<PedidoResponse>(
                        true,
                        "Pedido encontrado",
                        PedidoResponse.fromDomain(pedido)
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<List<PedidoResponse>>(
                        true,
                        "Tejidos encontrados",
                        pedidoPortIn.findAllIn()
                                .stream()
                                .map(PedidoResponse::fromDomain)
                                .toList()
                ));
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> findByUsuario(@PathVariable String username) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Pedidos encontrados del usuario "+username,
                        pedidoPortIn.findByUsuarioIn(username)
                                .stream()
                                .map(PedidoResponse::fromDomain)
                                .toList()
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PedidoResponse>> addItem(@Valid @RequestBody DetallePedidoRequest detallePedidoRequest) {
        Pedido pedidoCreado = pedidoPortIn.addItemIn(
                detallePedidoRequest.getPedidoId(),
                detallePedidoRequest.getTejidoId(),
                detallePedidoRequest.getCantidad());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<PedidoResponse>(
                        true,
                        "Item "+detallePedidoRequest.getTejidoId()+" añadido al pedido "+detallePedidoRequest.getPedidoId()+" con éxito",
                        PedidoResponse.fromDomain(pedidoCreado))
                );
    }

    @DeleteMapping("/{pedidoId}/items/{detalleId}")
    public ResponseEntity<ApiResponse<PedidoResponse>> removeItem(@PathVariable UUID pedidoId, @PathVariable UUID detalleId) {
        pedidoPortIn.removeItemIn(pedidoId, detalleId);
        Pedido pedidoModificado = pedidoPortIn.findByIdIn(pedidoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<PedidoResponse>(
                        true,
                        "Item "+detalleId+" eliminado del pedido "+pedidoId+" con éxito",
                        PedidoResponse.fromDomain(pedidoModificado))
                );
    }

    @PutMapping("/{pedidoId}/items/{detalleId}")
    public ResponseEntity<ApiResponse<PedidoResponse>> updateCantidad(
            @PathVariable UUID pedidoId,
            @PathVariable UUID detalleId,
            @Valid @RequestBody UpdateCantidad cantidadRequest
        ) {
        Pedido pedidoModificado = pedidoPortIn.updateCantidadIn(pedidoId, detalleId, cantidadRequest.getCantidad());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<PedidoResponse>(
                        true,
                        "Cantidad actualizada del item "+detalleId+" del pedido "+pedidoId,
                        PedidoResponse.fromDomain(pedidoModificado)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        pedidoPortIn.deleteIn(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<String>(
                        true,
                        "Pedido eliminado",
                        "id: "+id
                ));
    }

}
