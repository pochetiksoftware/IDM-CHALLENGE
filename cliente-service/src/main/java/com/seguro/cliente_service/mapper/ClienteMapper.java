package com.seguro.cliente_service.mapper;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .documento(request.documento())
                .nombre(request.nombre())
                .email(request.email())
                .estado(request.estado())
                .build();
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getDocumento(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getEstado()
        );
    }
}
