package com.seguro.poliza_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PolizaRequest(

        @NotBlank
        String documentoCliente,

        @NotBlank
        String tipoSeguro,

        @NotNull
        BigDecimal prima
) {}
