package com.dhh.apiRestSpringboot3.controller;

import com.dhh.apiRestSpringboot3.dto.ClientDTO;
import com.dhh.apiRestSpringboot3.dto.ClientResult;
import com.dhh.apiRestSpringboot3.service.ClientService;
import com.dhh.apiRestSpringboot3.service.OrchestatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;
    private final OrchestatorService orchestatorService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO clienteDTO) {
        return ResponseEntity.status(201).body(clientService.save(clienteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clienteDTO) {
        return ResponseEntity.ok(clientService.update(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultar-lote")
    public CompletableFuture<ResponseEntity<List<ClientResult>>> consultarLote(
            @RequestBody List<Long> productoIds) {
        // Llamada asíncrona, y responde con 200 OK y el body al resolver
        return orchestatorService.processLotClient(productoIds)
                .thenApply(ResponseEntity::ok);
    }

}