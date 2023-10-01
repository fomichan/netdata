package com.fomich.netdata.integration;




import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;


// Создадим тестовую конфигурацию, которая создаст тестовый контекст
// аннотация @TestConfiguration ищет @SpringBootApplication

@TestConfiguration
public class TestNetdataApplication {

    // Теперь Mock и Spy бины можно писать здесь и они будут в тестовом контексте
    // @SpyBean(name="pool1")
    // private ConnectionPool pool1;
}
