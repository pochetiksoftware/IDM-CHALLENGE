package com.seguro.poliza_service.service;

import com.seguro.poliza_service.client.ClienteClient;
import com.seguro.poliza_service.client.ClienteResponse;
import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.mapper.PolizaMapper;
import com.seguro.poliza_service.repository.PolizaRepository;
import com.seguro.poliza_service.risk.RiskEngine;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class PolizaServiceTest {

    ClienteClient clienteClient = mock(ClienteClient.class);
    PolizaRepository repository = mock(PolizaRepository.class);
    PolizaMapper mapper = new PolizaMapper();
    RiskEngine riskEngine = new RiskEngine();

    PolizaService service =
            new PolizaService(repository, mapper, clienteClient, riskEngine);

    @Test
    void emitirPolizaOK_clienteActivo() {

        PolizaRequest request =
                new PolizaRequest("123", "SALUD", BigDecimal.valueOf(1000));

        when(clienteClient.obtenerCliente("123"))
                .thenReturn(Mono.just(
                        new ClienteResponse(1L, "123", "Juan", "mail", "ACTIVO")
                ));

        when(repository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        StepVerifier.create(service.emitir(request))
                .expectNextMatches(resp ->
                        resp.estado().equals("EMITIDA")
                )
                .verifyComplete();

        verify(repository, times(1)).save(any());
    }

    @Test
    void emitirPolizaRechazada_clienteInactivo() {

        PolizaRequest request =
                new PolizaRequest("123", "SALUD", BigDecimal.valueOf(1000));

        when(clienteClient.obtenerCliente("123"))
                .thenReturn(Mono.just(
                        new ClienteResponse(1L, "123", "Juan", "mail", "INACTIVO")
                ));

        when(repository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        StepVerifier.create(service.emitir(request))
                .expectNextMatches(resp ->
                        resp.estado().equals("RECHAZADA")
                )
                .verifyComplete();
    }

    @Test
    void emitirPolizaPrimaAltaAuto_ajustePrima() {

        PolizaRequest request =
                new PolizaRequest("123", "AUTO", BigDecimal.valueOf(6000));

        when(clienteClient.obtenerCliente("123"))
                .thenReturn(Mono.just(
                        new ClienteResponse(1L, "123", "Juan", "mail", "ACTIVO")
                ));

        when(repository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        StepVerifier.create(service.emitir(request))
                .expectNextMatches(resp ->
                        resp.prima().compareTo(BigDecimal.valueOf(6600)) == 0
                )
                .verifyComplete();
    }
}
