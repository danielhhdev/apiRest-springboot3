package com.dhh.apiRestSpringboot3.service;

import com.dhh.apiRestSpringboot3.dto.ClientResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrchestatorService {

    public CompletableFuture<List<ClientResult>> processLotClient(List<Long> clienteIds);
}
