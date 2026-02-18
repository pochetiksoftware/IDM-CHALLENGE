package com.seguro.poliza_service.repository;

import com.seguro.poliza_service.entity.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza, Long> {

    List<Poliza> findByDocumentoCliente(String documentoCliente);
}
