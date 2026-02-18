package com.seguro.poliza_service.risk;

import java.math.BigDecimal;

public record RiskResult(
        RiskLevel nivel,
        BigDecimal primaAjustada,
        boolean aprobado,
        String motivo
) {}
