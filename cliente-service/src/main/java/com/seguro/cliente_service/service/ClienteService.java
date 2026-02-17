package com.seguro.cliente_service.service;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.mapper.ClienteMapper;
import com.seguro.cliente_service.model.Cliente;
import com.seguro.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponse crearCliente(ClienteRequest request) {

        Cliente cliente = ClienteMapper.toEntity(request);

        Cliente saved = repository.save(cliente);

        return ClienteMapper.toResponse(saved);
    }

    public ClienteResponse buscarPorDocumento(String documento) {

        Cliente cliente = repository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return ClienteMapper.toResponse(cliente);
    }
}
