package com.seguro.poliza_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "polizas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentoCliente;

    private String tipoSeguro;

    private BigDecimal prima;

    private String estado;
}
