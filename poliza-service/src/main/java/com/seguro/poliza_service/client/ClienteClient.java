package com.seguro.poliza_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(WebClient clienteWebClient) {
        this.webClient = clienteWebClient;
    }

    public Mono<ClienteResponse> obtenerCliente(String documento) {

        return webClient.get()
                .uri("/clientes/{documento}", documento)
                .retrieve()
                .bodyToMono(ClienteResponse.class);
    }
}
