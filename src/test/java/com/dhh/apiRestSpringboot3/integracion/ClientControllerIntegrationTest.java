package com.dhh.apiRestSpringboot3.integracion;

import com.dhh.apiRestSpringboot3.model.Client;
import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerIntegrationTest extends BaseIntegracionTest{

    @Autowired
    private ClientRepository clientRepository;

    // ---------- GET /api (todos los clientes) ----------
    @Test
    void getAll_deberiaDevolverListaClientes() throws Exception {
        // Arrange: Inserta algunos clientes
        clientRepository.save(new Client(null, "Ana", "ana@email.com"));
        clientRepository.save(new Client(null, "Juan", "juan@email.com"));

        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana"))
                .andExpect(jsonPath("$[1].nombre").value("Juan"));
    }
}
