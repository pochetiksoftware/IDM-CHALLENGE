package com.seguro.poliza_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;
import java.math.BigDecimal;

@Table("polizas")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Poliza {

    @Id
    private Long id;

    private String documentoCliente;
    private String tipoSeguro;
    private BigDecimal prima;
    private String estado;
}