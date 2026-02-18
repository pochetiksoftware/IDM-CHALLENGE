package com.seguro.poliza_service.service;

import com.seguro.poliza_service.client.ClienteClient;
import com.seguro.poliza_service.client.ClienteResponse;
import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.dto.PolizaResponse;
import com.seguro.poliza_service.entity.Poliza;
import com.seguro.poliza_service.mapper.PolizaMapper;
import com.seguro.poliza_service.repository.PolizaRepository;
import com.seguro.poliza_service.risk.RiskEngine;
import com.seguro.poliza_service.risk.RiskResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolizaService {

    private final PolizaRepository repository;
    private final PolizaMapper mapper;
    private final ClienteClient clienteClient;
    private final RiskEngine riskEngine;

    public Mono<PolizaResponse> emitir(PolizaRequest request) {

        return clienteClient.obtenerCliente(request.documentoCliente())
                .flatMap(cliente -> {

                    log.info("Cliente obtenido correctamente: {}", cliente.documento());

                    Poliza poliza = mapper.toEntity(request);

                    RiskResult result =
                            riskEngine.evaluar(poliza, cliente.estado());

                    poliza.setEstado(result.aprobado() ? "EMITIDA" : "RECHAZADA");
                    poliza.setPrima(result.primaAjustada());

                    Poliza saved = repository.save(poliza);

                    return Mono.just(mapper.toResponse(saved));
                })
                .onErrorResume(ex -> {

                    log.warn("Cliente service no disponible — fallback activado: {}", ex.getMessage());

                    Poliza poliza = mapper.toEntity(request);
                    poliza.setEstado("PENDIENTE_REVISION");

                    Poliza saved = repository.save(poliza);

                    return Mono.just(mapper.toResponse(saved));
                });
    }

    public List<PolizaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<PolizaResponse> porCliente(String documento) {
        return repository.findByDocumentoCliente(documento)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
