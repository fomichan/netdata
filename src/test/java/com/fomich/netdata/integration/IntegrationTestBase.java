package com.fomich.netdata.integration;

import com.fomich.netdata.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;





// Настраиваем здесь Testcontainers

@IT
@Sql({
        "classpath:sql/generate_data.sql"
})
public abstract class IntegrationTestBase {


    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource // определим какие порты БД будут назначены динамически
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
