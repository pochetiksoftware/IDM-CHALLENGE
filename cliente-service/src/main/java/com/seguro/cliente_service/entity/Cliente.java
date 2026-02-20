package com.seguro.cliente_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id
    private Long id;

    private String documento;
    private String nombre;
    private String email;
    private String estado;
}