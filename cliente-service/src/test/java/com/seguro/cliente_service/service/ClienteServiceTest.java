package com.seguro.cliente_service.service;

import com.seguro.cliente_service.dto.ClienteRequest;
import com.seguro.cliente_service.dto.ClienteResponse;
import com.seguro.cliente_service.entity.Cliente;
import com.seguro.cliente_service.exception.ResourceNotFoundException;
import com.seguro.cliente_service.mapper.ClienteMapper;
import com.seguro.cliente_service.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequest request;
    private ClienteResponse response;

    @BeforeEach
    void setUp() {
        // Datos de prueba
        request = new ClienteRequest("12345", "Pepe Perez", "pepe@ntt.com", "ACTIVO");
        cliente = new Cliente(1L, "12345", "Pepe Perez", "pepe@ntt.com", "ACTIVO");
        response = new ClienteResponse(1L, "12345", "Pepe Perez", "pepe@ntt.com", "ACTIVO");
    }

    @Test
    @DisplayName("Debe crear un cliente exitosamente")
    void crearClienteOk() {
        when(repository.existsByDocumento(anyString())).thenReturn(false);
        when(mapper.toEntity(any(ClienteRequest.class))).thenReturn(cliente);
        when(repository.save(any(Cliente.class))).thenReturn(cliente);
        when(mapper.toResponse(any(Cliente.class))).thenReturn(response);

        StepVerifier.create(clienteService.crear(request))
                .expectNext(response)
                .verifyComplete();

        verify(repository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe lanzar error si el cliente ya existe")
    void crearClienteErrorYaExiste() {

        when(repository.existsByDocumento(anyString())).thenReturn(true);

        StepVerifier.create(clienteService.crear(request))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Cliente ya existe"))
                .verify();

        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe buscar un cliente por documento exitosamente")
    void buscarPorDocumentoOk() {

        String doc = "12345";
        when(repository.findByDocumento(doc)).thenReturn(Optional.of(cliente));
        when(mapper.toResponse(cliente)).thenReturn(response);

        StepVerifier.create(clienteService.buscarPorDocumento(doc))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el cliente no existe")
    void buscarPorDocumentoNotFound() {
        String doc = "999";
        when(repository.findByDocumento(doc)).thenReturn(Optional.empty());

        StepVerifier.create(clienteService.buscarPorDocumento(doc))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}
