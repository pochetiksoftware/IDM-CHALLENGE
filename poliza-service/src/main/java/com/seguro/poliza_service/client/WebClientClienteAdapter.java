package com.seguro.poliza_service.client;


import com.seguro.poliza_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class WebClientClienteAdapter implements ClientePort {

    private final WebClient clienteWebClient;

    @Override
    public Mono<ClienteResponse> obtenerCliente(String documento) {
        return clienteWebClient.get()
                .uri("/clientes/{documento}", documento)
                .retrieve()
                .onStatus(s -> s.value() == 404,
                        r -> Mono.error(new ResourceNotFoundException("Cliente no encontrado")))
                .onStatus(s -> s.is5xxServerError(),
                        r -> Mono.error(new RuntimeException("Cliente service 5xx")))
                .bodyToMono(ClienteResponse.class)
                .timeout(Duration.ofSeconds(2));
    }
}
