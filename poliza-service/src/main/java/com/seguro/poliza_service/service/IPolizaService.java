package com.seguro.poliza_service.service;

import com.seguro.poliza_service.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPolizaService {

    Mono<PolizaResponse> emitir(PolizaRequest request);

    Flux<PolizaResponse> listar();

    Flux<PolizaResponse> porCliente(String documento);
}
