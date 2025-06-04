package com.dhh.apiRestSpringboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ApiRestSpringboot3Application {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestSpringboot3Application.class, args);
    }

}
