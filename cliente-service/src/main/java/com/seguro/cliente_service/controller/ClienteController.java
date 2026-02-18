package com.seguro.cliente_service.controller;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public Mono<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        return service.crear(request);
    }

    @GetMapping("/{documento}")
    public Mono<ClienteResponse> buscar(@PathVariable String documento) {
        return service.buscarPorDocumento(documento);
    }
}
