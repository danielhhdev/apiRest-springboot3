package com.dhh.apiRestSpringboot3.service;

import com.dhh.apiRestSpringboot3.dto.ClientResult;

import java.util.concurrent.CompletableFuture;

public interface ClientThreadvirtualService {

    public CompletableFuture<ClientResult> processClient(Long clienteId);;
}
