package com.dhh.apiRestSpringboot3.service.impl;

import com.dhh.apiRestSpringboot3.dto.ClientResult;
import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import com.dhh.apiRestSpringboot3.service.ClientThreadvirtualService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ClientThreadVirtualServiceImpl implements ClientThreadvirtualService {

    private final ClientRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ClientThreadVirtualServiceImpl.class);

    @Async("virtualThreadTaskExecutor")
    public CompletableFuture<ClientResult> processClient(Long clienteId) {
        String client;
        try {
            logger.info("Procesando cliente {} en hilo {}", clienteId, Thread.currentThread());
            Thread.sleep(2000);
            client = repository.findById(clienteId).map(Client::getName).orElse("NO existe cliente");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int amount = (int) (Math.random() * 5000);

        return CompletableFuture.completedFuture(
                new ClientResult(clienteId, client, amount)
        );
    }
}
