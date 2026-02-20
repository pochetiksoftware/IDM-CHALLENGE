package com.seguro.cliente_service.repository;

import com.seguro.cliente_service.entity.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClienteRepository extends ReactiveCrudRepository<Cliente, Long> {

    Mono<Boolean> existsByDocumento(String documento);
    Mono<Cliente> findByDocumento(String documento);
}