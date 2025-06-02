package com.dhh.apiRestSpringboot3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - API-REST en Spring Boot 3.X")
                        .version("1.0.0")
                        .description("API REST con Spring Boot 3.x, Virtual Threads y PostgreSQL")
                        .contact(new Contact()
                                .name("Daniel Hernández")
                                .email("daniel@email.com")
                                .url("https://github.com/danielhhdev")));
    }
}