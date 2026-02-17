package com.seguro.cliente_service.dto;

public record ClienteResponse(
        Long id,
        String nombre,
        String documento,
        String email,
        String estado
) {}
