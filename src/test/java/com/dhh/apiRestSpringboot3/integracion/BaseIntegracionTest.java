package com.dhh.apiRestSpringboot3.integracion;


import com.dhh.apiRestSpringboot3.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegracionTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    // --- Static block: Espera activa a que el contenedor esté listo ---
    static {
        postgres.start();
        // Espera activa, reintenta hasta 10 veces durante 10 segundos en total
        int attempts = 0;
        int maxAttempts = 10;
        while (true) {
            try (Connection c = DriverManager.getConnection(
                    postgres.getJdbcUrl(),
                    postgres.getUsername(),
                    postgres.getPassword())) {
                // Conexión exitosa, la base está lista
                break;
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Cannot connect to PostgreSQL container after several attempts", e);
                }
                try {
                    Thread.sleep(1000); // Espera 1 segundo antes de reintentar
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    // Opcional: Limpieza de la base de datos antes de cada test
    @BeforeEach
    void cleanDatabase(@Autowired ClientRepository repo) {
        repo.deleteAll();
    }
}
