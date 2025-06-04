package com.dhh.apiRestSpringboot3.service;


import com.dhh.apiRestSpringboot3.dto.ClientDTO;
import com.dhh.apiRestSpringboot3.mapper.ClientMapper;
import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import com.dhh.apiRestSpringboot3.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl service;

    // ---------- findAll ----------

    @Test
    void findAll_deberiaDevolverListaDeClientesDTO() {
        Client client1 = new Client(1L, "Ana", "ana@email.com");
        Client client2 = new Client(2L, "Juan", "juan@email.com");

        when(repository.findAll()).thenReturn(List.of(client1, client2));
        when(clientMapper.toDTO(client1)).thenReturn(new ClientDTO(1L, "Ana", "ana@email.com"));
        when(clientMapper.toDTO(client2)).thenReturn(new ClientDTO(2L, "Juan", "juan@email.com"));

        List<ClientDTO> resultado = service.findAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getName()).isEqualTo("Ana");
        assertThat(resultado.get(1).getName()).isEqualTo("Juan");

        verify(repository).findAll();
        verify(clientMapper).toDTO(client1);
        verify(clientMapper).toDTO(client2);
    }

    // ---------- save ----------

    @Test
    void save_deberiaGuardarYDevolverDTO() {
        ClientDTO dtoIn = new ClientDTO(null, "Luis", "luis@email.com");
        Client entity = new Client(null, "Luis", "luis@email.com");
        Client saved = new Client(3L, "Luis", "luis@email.com");
        ClientDTO dtoOut = new ClientDTO(3L, "Luis", "luis@email.com");

        when(clientMapper.toEntity(dtoIn)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(clientMapper.toDTO(saved)).thenReturn(dtoOut);

        ClientDTO resultado = service.save(dtoIn);

        assertThat(resultado).isEqualTo(dtoOut);
        verify(clientMapper).toEntity(dtoIn);
        verify(repository).save(entity);
        verify(clientMapper).toDTO(saved);
    }

    @Test
    void save_deberiaLanzarRuntimeException_siFallaRepositorio() {
        ClientDTO dtoIn = new ClientDTO(null, "Luis", "luis@email.com");
        Client entity = new Client(null, "Luis", "luis@email.com");

        when(clientMapper.toEntity(dtoIn)).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> service.save(dtoIn))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB error");

        verify(repository).save(entity);
    }

    // ---------- update ----------

    @Test
    void update_deberiaActualizarYDevolverDTO() {
        ClientDTO dtoIn = new ClientDTO(null, "Luis", "luis@email.com");
        ClientDTO dtoWithId = new ClientDTO(3L, "Luis", "luis@email.com");
        Client entity = new Client(3L, "Luis", "luis@email.com");
        Client saved = new Client(3L, "Luis", "luis@email.com");
        ClientDTO dtoOut = new ClientDTO(3L, "Luis", "luis@email.com");

        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(entity);
        when(repository.save(any(Client.class))).thenReturn(saved);
        when(clientMapper.toDTO(saved)).thenReturn(dtoOut);

        ClientDTO resultado = service.update(3L, dtoIn);

        assertThat(resultado).isEqualTo(dtoOut);
        verify(clientMapper).toEntity(any(ClientDTO.class));
        verify(repository).save(any(Client.class));
        verify(clientMapper).toDTO(saved);
    }

    @Test
    void update_deberiaLanzarRuntimeException_siFallaRepositorio() {
        ClientDTO dtoIn = new ClientDTO(null, "Luis", "luis@email.com");
        ClientDTO dtoWithId = new ClientDTO(4L, "Luis", "luis@email.com");
        Client entity = new Client(4L, "Luis", "luis@email.com");

        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> service.update(4L, dtoIn))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB error");

        verify(repository).save(any(Client.class));
    }

    // ---------- delete ----------

    @Test
    void delete_deberiaEliminarPorId() {
        Long id = 5L;

        service.delete(id);

        verify(repository).deleteById(id);
    }



}
