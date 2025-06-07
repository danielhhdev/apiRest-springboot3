package com.dhh.apiRestSpringboot3;

import com.dhh.apiRestSpringboot3.integracion.BaseIntegracionTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApiRestSpringboot3ApplicationTests extends BaseIntegracionTest {

    @Test
    void contextLoads() {
    }

}
