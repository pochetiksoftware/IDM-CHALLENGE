package com.seguro.poliza_service.dto;

import java.math.BigDecimal;

public record PolizaResponse(
        Long id,
        String documentoCliente,
        String tipoSeguro,
        BigDecimal prima,
        String estado
) {}
