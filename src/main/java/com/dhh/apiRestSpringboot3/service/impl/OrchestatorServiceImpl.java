package com.dhh.apiRestSpringboot3.service.impl;

import com.dhh.apiRestSpringboot3.dto.ClientResult;
import com.dhh.apiRestSpringboot3.service.ClientThreadvirtualService;
import com.dhh.apiRestSpringboot3.service.OrchestatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class OrchestatorServiceImpl implements OrchestatorService {

    private final ClientThreadvirtualService clientThreadvirtualService;

    public CompletableFuture<List<ClientResult>> processLotClient(List<Long> clienteIds) {
        List<CompletableFuture<ClientResult>> futures =
                clienteIds.stream()
                        .map(clientThreadvirtualService::processClient) // ¡Llama al proxy real!
                        .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList());
    }
}
