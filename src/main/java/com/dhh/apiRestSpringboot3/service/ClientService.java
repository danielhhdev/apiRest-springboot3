package com.dhh.apiRestSpringboot3.service;

import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;


    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client save(Client clienteDTO) {
        return repository.save(clienteDTO);
    }

    public Client update(Long id, Client clienteDTO) {
        clienteDTO.setId(id);
        return repository.save(clienteDTO);

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
