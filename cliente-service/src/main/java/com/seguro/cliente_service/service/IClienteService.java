package com.seguro.cliente_service.service;

import com.seguro.cliente_service.dto.ClienteRequest;
import com.seguro.cliente_service.dto.ClienteResponse;
import reactor.core.publisher.Mono;

public interface IClienteService {
    Mono<ClienteResponse> crear(ClienteRequest request);
    Mono<ClienteResponse> buscarPorDocumento(String documento);
}
