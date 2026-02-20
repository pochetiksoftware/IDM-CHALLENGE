package com.seguro.poliza_service.service;

import com.seguro.poliza_service.client.ClienteClient;
import com.seguro.poliza_service.client.ClienteResponse;
import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.dto.PolizaResponse;
import com.seguro.poliza_service.entity.Poliza;
import com.seguro.poliza_service.mapper.PolizaMapper;
import com.seguro.poliza_service.repository.PolizaRepository;
import com.seguro.poliza_service.risk.RiskEngine;
import com.seguro.poliza_service.risk.RiskLevel;
import com.seguro.poliza_service.risk.RiskResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolizaServiceTest {

    @Mock
    private PolizaRepository repository;

    @Mock
    private PolizaMapper mapper;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private RiskEngine riskEngine;

    @InjectMocks
    private PolizaService polizaService;

    private PolizaRequest request;
    private Poliza poliza;
    private ClienteResponse clienteRes;
    private PolizaResponse response;

    @BeforeEach
    void setUp() {
        request = new PolizaRequest("123", "AUTO", BigDecimal.valueOf(1000));

        poliza = new Poliza(1L, "123", "AUTO", BigDecimal.valueOf(1000), "PENDIENTE");

        clienteRes = new ClienteResponse(1L, "123", "Juan", "juan@ntt.com", "ACTIVO");

        response = new PolizaResponse(1L, "123", "AUTO", BigDecimal.valueOf(1000), "EMITIDA");
    }

    @Test
    @DisplayName("Debe emitir póliza como EMITIDA cuando el riesgo es aprobado")
    void emitirPolizaOk() {
        RiskResult riskOk = new RiskResult(RiskLevel.BAJO, BigDecimal.valueOf(1000), true, "OK");

        when(clienteClient.obtenerCliente(anyString())).thenReturn(Mono.just(clienteRes));
        when(mapper.toEntity(any())).thenReturn(poliza);
        when(riskEngine.evaluar(any(), anyString())).thenReturn(riskOk);
        when(repository.save(any())).thenReturn(poliza);
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(polizaService.emitir(request))
                .expectNextMatches(res -> res.estado().equals("EMITIDA"))
                .verifyComplete();

        verify(repository).save(any(Poliza.class));
    }

    @Test
    @DisplayName("Debe emitir póliza como RECHAZADA cuando el cliente está inactivo")
    void emitirPolizaRechazada() {
        ClienteResponse clienteInactivo = new ClienteResponse(1L, "123", "Juan", "j@j.com", "INACTIVO");
        RiskResult riskAlto = new RiskResult(RiskLevel.ALTO, BigDecimal.valueOf(1000), false, "Cliente inactivo");

        when(clienteClient.obtenerCliente(anyString())).thenReturn(Mono.just(clienteInactivo));
        when(mapper.toEntity(any())).thenReturn(poliza);
        when(riskEngine.evaluar(any(), anyString())).thenReturn(riskAlto);
        when(repository.save(any())).thenReturn(poliza);

        PolizaResponse resRechazada = new PolizaResponse(1L, "123", "AUTO", BigDecimal.valueOf(1000), "RECHAZADA");
        when(mapper.toResponse(any())).thenReturn(resRechazada);

        StepVerifier.create(polizaService.emitir(request))
                .expectNextMatches(res -> res.estado().equals("RECHAZADA"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe activar fallback PENDIENTE_REVISION cuando falla el microservicio cliente")
    void emitirPolizaFallback() {
        when(clienteClient.obtenerCliente(anyString())).thenReturn(Mono.error(new RuntimeException("Servicio no disponible")));
        when(mapper.toEntity(any())).thenReturn(poliza);
        when(repository.save(any())).thenReturn(poliza);

        PolizaResponse resPendiente = new PolizaResponse(1L, "123", "AUTO", BigDecimal.valueOf(1000), "PENDIENTE_REVISION");
        when(mapper.toResponse(any())).thenReturn(resPendiente);

        StepVerifier.create(polizaService.emitir(request))
                .expectNextMatches(res -> res.estado().equals("PENDIENTE_REVISION"))
                .verifyComplete();

        verify(riskEngine, never()).evaluar(any(), anyString());
    }

    @Test
    @DisplayName("Debe listar todas las pólizas")
    void listarPolizas() {
        when(repository.findAll()).thenReturn(java.util.List.of(poliza));
        when(mapper.toResponse(any())).thenReturn(response);
        var resultado = polizaService.listar();
        org.junit.jupiter.api.Assertions.assertFalse(resultado.isEmpty());
        org.junit.jupiter.api.Assertions.assertEquals(1, resultado.size());
    }
}
