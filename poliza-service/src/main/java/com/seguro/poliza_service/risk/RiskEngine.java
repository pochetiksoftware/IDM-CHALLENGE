package com.seguro.poliza_service.risk;

import com.seguro.poliza_service.entity.Poliza;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RiskEngine {

    public RiskResult evaluar(Poliza poliza, String estadoCliente) {

        Predicate<String> clienteActivo =
                estado -> "ACTIVO".equalsIgnoreCase(estado);

        Predicate<Poliza> primaAlta =
                p -> p.getPrima().compareTo(BigDecimal.valueOf(5000)) > 0;

        Predicate<Poliza> seguroAuto =
                p -> "AUTO".equalsIgnoreCase(p.getTipoSeguro());

        if (!clienteActivo.test(estadoCliente)) {
            return new RiskResult(
                    RiskLevel.ALTO,
                    poliza.getPrima(),
                    false,
                    "Cliente inactivo"
            );
        }

        BigDecimal primaAjustada = poliza.getPrima();
        RiskLevel nivel = RiskLevel.BAJO;
        String motivo = "OK";

        List<Predicate<Poliza>> reglas = List.of(primaAlta, seguroAuto);

        boolean revisar = reglas.stream().allMatch(r -> r.test(poliza));

        if (revisar) {
            nivel = RiskLevel.MEDIO;
            motivo = "Prima alta en AUTO";
            primaAjustada = primaAjustada.multiply(BigDecimal.valueOf(1.10));
        }

        return new RiskResult(
                nivel,
                primaAjustada,
                true,
                motivo
        );
    }
}
