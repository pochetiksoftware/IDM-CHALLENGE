package com.seguro.cliente_service.service;

import com.seguro.cliente_service.dto.*;
import com.seguro.cliente_service.mapper.ClienteMapper;
import com.seguro.cliente_service.repository.ClienteRepository;
import com.seguro.cliente_service.service.impl.ClienteService;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ClienteServiceTest {

    ClienteRepository repository = mock(ClienteRepository.class);
    ClienteMapper mapper = new ClienteMapper();

    ClienteService service =
            new ClienteService(repository, mapper);

    @Test
    void crearClienteOK() {

        ClienteRequest request =
                new ClienteRequest("123", "Juan", "test@test.com", "ACTIVO");

        when(repository.existsByDocumento("123")).thenReturn(false);

        when(repository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        StepVerifier.create(service.crear(request))
                .expectNextMatches(resp -> resp.documento().equals("123"))
                .verifyComplete();
    }
}
