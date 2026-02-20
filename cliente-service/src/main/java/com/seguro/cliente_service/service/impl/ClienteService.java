package com.seguro.cliente_service.service.impl;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.exception.ResourceNotFoundException;
import com.seguro.cliente_service.mapper.ClienteMapper;
import com.seguro.cliente_service.repository.ClienteRepository;
import com.seguro.cliente_service.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Override
    public Mono<ClienteResponse> crear(ClienteRequest req) {
        return repository.existsByDocumento(req.documento())
                .flatMap(existe -> existe
                        ? Mono.error(new RuntimeException("Cliente ya existe"))
                        : repository.save(mapper.toEntity(req)).map(mapper::toResponse));
    }

    @Override
    public Mono<ClienteResponse> buscarPorDocumento(String documento) {
        return repository.findByDocumento(documento)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente no encontrado")))
                .map(mapper::toResponse);
    }
}