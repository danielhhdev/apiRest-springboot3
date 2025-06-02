package com.dhh.apiRestSpringboot3.service;

import com.dhh.apiRestSpringboot3.dto.ClientDTO;
import com.dhh.apiRestSpringboot3.mapper.ClientMapper;
import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;


    public List<ClientDTO> findAll() {
        var listClientes = repository.findAll();

        return listClientes.stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    public ClientDTO save(ClientDTO clienteDTO) {
        Client cliente;

        try {
            cliente = repository.save(clientMapper.toEntity(clienteDTO));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return clientMapper.toDTO(cliente);
    }

    public ClientDTO update(Long id, ClientDTO clienteDTO) {
        clienteDTO.setId(id);
        Client cliente;
        try {
            cliente = repository.save(clientMapper.toEntity(clienteDTO));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return clientMapper.toDTO(cliente);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
