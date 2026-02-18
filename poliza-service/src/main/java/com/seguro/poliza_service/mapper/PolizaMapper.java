package com.seguro.poliza_service.mapper;

import com.seguro.poliza_service.dto.PolizaRequest;
import com.seguro.poliza_service.dto.PolizaResponse;
import com.seguro.poliza_service.entity.Poliza;
import org.springframework.stereotype.Component;

@Component
public class PolizaMapper {

    public PolizaResponse toResponse(Poliza poliza) {
        return new PolizaResponse(
                poliza.getId(),
                poliza.getDocumentoCliente(),
                poliza.getTipoSeguro(),
                poliza.getPrima(),
                poliza.getEstado()
        );
    }

    public Poliza toEntity(PolizaRequest request) {
        return Poliza.builder()
                .documentoCliente(request.documentoCliente())
                .tipoSeguro(request.tipoSeguro())
                .prima(request.prima())
                .estado("PENDIENTE")
                .build();
    }
}
