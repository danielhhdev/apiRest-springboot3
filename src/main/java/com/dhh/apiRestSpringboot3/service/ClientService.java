package com.dhh.apiRestSpringboot3.service;

import com.dhh.apiRestSpringboot3.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    public List<ClientDTO> findAll();

    public ClientDTO save(ClientDTO clienteDTO);

    public ClientDTO update(Long id, ClientDTO clienteDTO);

    public void delete(Long id);

}
