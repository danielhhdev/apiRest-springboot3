package com.dhh.apiRestSpringboot3.service;


import com.dhh.apiRestSpringboot3.dto.ClientResult;
import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import com.dhh.apiRestSpringboot3.service.impl.ClientThreadVirtualServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientThreadVirtualServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientThreadVirtualServiceImpl service;


    @Test
    void processClient_deberiaDevolverResultadoCorrecto_siExisteCliente() throws Exception {
        Long clienteId = 1L;
        Client clientEntity = new Client(clienteId, "Pepe", "pepe@email.com");
        when(repository.findById(clienteId)).thenReturn(Optional.of(clientEntity));

        CompletableFuture<ClientResult> future = service.processClient(clienteId);

        // Para test unitario, puedes usar .get() (pero recuerda: es un test de lógica, no de rendimiento)
        ClientResult result = future.get();

        assertThat(result.id()).isEqualTo(clienteId);
        assertThat(result.name()).isEqualTo("Pepe");
        assertThat(result.amount()).isBetween(0, 5000);

        verify(repository).findById(clienteId);
    }

    @Test
    void processClient_deberiaDevolverNOExisteCliente_siNoExiste() throws Exception {
        Long clienteId = 2L;
        when(repository.findById(clienteId)).thenReturn(Optional.empty());

        CompletableFuture<ClientResult> future = service.processClient(clienteId);
        ClientResult result = future.get();

        assertThat(result.id()).isEqualTo(clienteId);
        assertThat(result.name()).isEqualTo("NO existe cliente");
        assertThat(result.amount()).isBetween(0, 5000);

        verify(repository).findById(clienteId);
    }

    @Test
    void processClient_lanzaExcepcion_siRepositoryFalla() {
        Long clienteId = 3L;
        when(repository.findById(clienteId)).thenThrow(new RuntimeException("DB error"));

        // El future debería completar exceptionally
        assertThatThrownBy(() -> service.processClient(clienteId).get())
                .hasCauseInstanceOf(RuntimeException.class)
                .hasRootCauseMessage("DB error");

        verify(repository).findById(clienteId);
    }

}
