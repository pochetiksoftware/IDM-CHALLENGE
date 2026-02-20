package com.seguro.poliza_service.repository;

import com.seguro.poliza_service.entity.Poliza;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PolizaRepository extends ReactiveCrudRepository<Poliza, Long> {
    Flux<Poliza> findByDocumentoCliente(String documentoCliente);
}