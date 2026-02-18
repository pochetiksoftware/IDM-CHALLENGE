package com.seguro.poliza_service.controller;

import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.dto.PolizaResponse;
import com.seguro.poliza_service.service.PolizaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService service;

    @PostMapping
    public Mono<PolizaResponse> emitir(@Valid @RequestBody PolizaRequest request) {
        return service.emitir(request);
    }

    @GetMapping
    public Mono<List<PolizaResponse>> listar() {
        return Mono.fromCallable(service::listar)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/cliente/{documento}")
    public Mono<List<PolizaResponse>> porCliente(@PathVariable String documento) {
        return Mono.fromCallable(() -> service.porCliente(documento))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
