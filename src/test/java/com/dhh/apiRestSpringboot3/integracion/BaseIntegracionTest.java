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
        // Espera activa a que la base responda, muy importante en CI/CD
        try (Connection c = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword())) {
            // Si conecta, OK; si no, lanza excepción y falla rápido
        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to PostgreSQL container", e);
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
