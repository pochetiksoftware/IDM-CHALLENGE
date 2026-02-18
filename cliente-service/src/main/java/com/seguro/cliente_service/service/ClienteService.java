package com.seguro.cliente_service.service;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.entity.Cliente;
import com.seguro.cliente_service.exception.ResourceNotFoundException;
import com.seguro.cliente_service.mapper.ClienteMapper;
import com.seguro.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public Mono<ClienteResponse> crear(ClienteRequest request) {

        return Mono.fromCallable(() -> {

            if (repository.existsByDocumento(request.documento())) {
                throw new RuntimeException("Cliente ya existe");
            }

            Cliente cliente = mapper.toEntity(request);

            Cliente guardado = repository.save(cliente);

            return mapper.toResponse(guardado);

        });
    }

    public Mono<ClienteResponse> buscarPorDocumento(String documento) {

        return Mono.fromCallable(() ->
                repository.findByDocumento(documento)
                        .map(mapper::toResponse)
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"))
        );
    }
}
