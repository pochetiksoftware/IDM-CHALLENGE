package com.seguro.poliza_service.client;

public record ClienteResponse(
        Long id,
        String documento,
        String nombre,
        String email,
        String estado
) {}
