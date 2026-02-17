package com.seguro.cliente_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(

        @NotBlank String nombre,
        @NotBlank String documento,
        @Email String email,
        @NotBlank String estado

) {}
