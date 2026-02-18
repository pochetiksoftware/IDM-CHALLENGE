package com.seguro.cliente_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank String documento,
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @NotBlank String estado
) {}
