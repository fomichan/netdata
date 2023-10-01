package com.fomich.netdata.integration.service;

import com.fomich.netdata.dto.MultiplexerIdDto;
import com.fomich.netdata.integration.IntegrationTestBase;
import com.fomich.netdata.integration.annotation.IT;
import com.fomich.netdata.service.MultiplexerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
// указываем какой контекст интегрировать в тест. initializers позволяет найти application-test.yml
//@ContextConfiguration(classes = NetdataApplication.class,
//initializers = ConfigDataApplicationContextInitializer.class)


@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // чтобы инжектить бины из spring в тестовый контекст

// Если мы создадим другой ServiceIT с отличающимися настройкам (от @IT), например без @ActiveProfiles("test"),
// то для этого ServiceIT загрузится отдельный spring контекст
// Если в новом ServiceIT поставиь тоже IT то спринг создаст один контекст для двух интеграционных тестов
// Т.е. TestContext переиспользовал существующий


// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Можно указать эту аннотацию чтобы контекст не переиспользовался, а был свой в каждом тестовом методе (если мы что-то портим в контексте)
// Можно указывать и над методом
// Стараться не портить и переиспользовать контекст!!!
public class MultiplexerServiceIT {


    final MultiplexerService multiplexerService; // инжектим наш сервис
    private static final Integer MUX_ID = 1;

    // Можно заинжектить какой-то mock или spy. Тогда вызовется 2 спринг контекста, т.к. контексты снова отличаются
    // Или указать его в классе с @TestConfiguration, тогда он буден доступен всем
    // @SpyBean(name = "pool1")
    // private ConnectionPool pool1;

    @Test
    void findById() {
        var actualResult = multiplexerService.findById(MUX_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new MultiplexerIdDto(MUX_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }

}
