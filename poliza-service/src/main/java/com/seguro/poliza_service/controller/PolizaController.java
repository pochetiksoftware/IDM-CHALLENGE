package com.seguro.poliza_service.controller;

import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.dto.PolizaResponse;
import com.seguro.poliza_service.service.IPolizaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final IPolizaService service;

    @PostMapping
    public Mono<PolizaResponse> emitir(@Valid @RequestBody PolizaRequest request) {
        return service.emitir(request);
    }

    @GetMapping
    public Flux<PolizaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/cliente/{documento}")
    public Flux<PolizaResponse> porCliente(@PathVariable String documento) {
        return service.porCliente(documento);
    }
}
