package com.dhh.apiRestSpringboot3.service;


import com.dhh.apiRestSpringboot3.dto.ClientResult;
import com.dhh.apiRestSpringboot3.service.impl.OrchestatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrchestatorServiceTest {

    @Mock
    private ClientThreadvirtualService clientThreadvirtualService;

    @InjectMocks
    private OrchestatorServiceImpl orchestatorService;

    @Test
    void processLotClient_deberiaProcesarTodosLosIds() throws Exception {
        // Datos de prueba
        List<Long> ids = List.of(1L, 2L, 3L);

        // Mocks de resultados
        ClientResult result1 = new ClientResult(1L, "Ana", 100);
        ClientResult result2 = new ClientResult(2L, "Pepe", 200);
        ClientResult result3 = new ClientResult(3L, "Juan", 300);

        when(clientThreadvirtualService.processClient(1L)).thenReturn(CompletableFuture.completedFuture(result1));
        when(clientThreadvirtualService.processClient(2L)).thenReturn(CompletableFuture.completedFuture(result2));
        when(clientThreadvirtualService.processClient(3L)).thenReturn(CompletableFuture.completedFuture(result3));

        // Ejecutar el método
        CompletableFuture<List<ClientResult>> future = orchestatorService.processLotClient(ids);
        List<ClientResult> resultados = future.get();

        // Verificaciones
        assertThat(resultados).containsExactly(result1, result2, result3);

        // Verificar que se llama el método del servicio para cada id
        verify(clientThreadvirtualService).processClient(1L);
        verify(clientThreadvirtualService).processClient(2L);
        verify(clientThreadvirtualService).processClient(3L);
    }

    @Test
    void processLotClient_deberiaPropagarExcepcionSiAlgunoFalla() {
        List<Long> ids = List.of(1L, 2L);
        ClientResult result1 = new ClientResult(1L, "Ana", 100);

        when(clientThreadvirtualService.processClient(1L)).thenReturn(CompletableFuture.completedFuture(result1));
        when(clientThreadvirtualService.processClient(2L)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Fallo en cliente 2")));

        CompletableFuture<List<ClientResult>> future = orchestatorService.processLotClient(ids);

        assertThatThrownBy(future::get)
                .hasCauseInstanceOf(RuntimeException.class)
                .hasRootCauseMessage("Fallo en cliente 2");

        verify(clientThreadvirtualService).processClient(1L);
        verify(clientThreadvirtualService).processClient(2L);
    }



}
