package com.seguro.cliente_service.mapper;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.model.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .nombre(request.nombre())
                .documento(request.documento())
                .email(request.email())
                .estado(request.estado())
                .build();
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDocumento(),
                cliente.getEmail(),
                cliente.getEstado()
        );
    }
}
