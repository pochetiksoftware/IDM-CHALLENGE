package com.seguro.poliza_service.client;

import reactor.core.publisher.Mono;

public interface ClientePort {
    Mono<ClienteResponse> obtenerCliente(String documento);
}