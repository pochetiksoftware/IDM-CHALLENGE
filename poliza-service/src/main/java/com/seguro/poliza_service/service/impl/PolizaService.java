package com.seguro.poliza_service.service.impl;

import com.seguro.poliza_service.client.ClientePort;
import com.seguro.poliza_service.dto.*;
import com.seguro.poliza_service.entity.Poliza;
import com.seguro.poliza_service.exception.ResourceNotFoundException;
import com.seguro.poliza_service.mapper.PolizaMapper;
import com.seguro.poliza_service.repository.PolizaRepository;
import com.seguro.poliza_service.risk.*;
import com.seguro.poliza_service.service.IPolizaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolizaService implements IPolizaService {

    private final PolizaRepository repository;
    private final PolizaMapper mapper;
    private final ClientePort clientePort;
    private final RiskEngine riskEngine;
    private final ReactiveCircuitBreakerFactory<?, ?> cbFactory;

    public Mono<PolizaResponse> emitir(PolizaRequest request) {

        var cb = cbFactory.create("clienteService");

        Mono<PolizaResponse> flujo = clientePort.obtenerCliente(request.documentoCliente())
                .flatMap(cliente -> {
                    Poliza poliza = mapper.toEntity(request);

                    RiskResult result = riskEngine.evaluar(poliza, cliente.estado());

                    poliza.setEstado(result.aprobado() ? "EMITIDA" : "RECHAZADA");
                    poliza.setPrima(result.primaAjustada());

                    return repository.save(poliza).map(mapper::toResponse);
                });

        return cb.run(
                flujo,
                ex -> (ex instanceof ResourceNotFoundException)
                        ? Mono.error(ex)
                        : fallbackEmitir(request, ex)
        );
    }

    private Mono<PolizaResponse> fallbackEmitir(PolizaRequest request, Throwable ex) {
        log.warn("Fallback cliente-service (doc={}): {} - {}",
                request.documentoCliente(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );

        Poliza poliza = mapper.toEntity(request);
        poliza.setEstado("PENDIENTE_REVISION");

        return repository.save(poliza).map(mapper::toResponse);
    }

    @Override
    public Flux<PolizaResponse> listar() {
        return repository.findAll().map(mapper::toResponse);
    }

    @Override
    public Flux<PolizaResponse> porCliente(String documento) {
        return repository.findByDocumentoCliente(documento).map(mapper::toResponse);
    }
}