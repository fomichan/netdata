package com.fomich.netdata.integration.service;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.integration.IntegrationTestBase;
import com.fomich.netdata.integration.annotation.IT;
import com.fomich.netdata.service.MultiplexerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
// указываем какой контекст интегрировать в тест. initializers позволяет найти application-test.yml
//@ContextConfiguration(classes = NetdataApplication.class,
//initializers = ConfigDataApplicationContextInitializer.class)




//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // чтобы инжектить бины из spring в тестовый контекст

// Если мы создадим другой ServiceIT с отличающимися настройкам (от @IT), например без @ActiveProfiles("test"),
// то для этого ServiceIT загрузится отдельный spring контекст
// Если в новом ServiceIT поставиь тоже IT то спринг создаст один контекст для двух интеграционных тестов
// Т.е. TestContext переиспользовал существующий

// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Можно указать эту аннотацию чтобы контекст не переиспользовался, а был свой в каждом тестовом методе (если мы что-то портим в контексте)
// Можно указывать и над методом
// Стараться не портить и переиспользовать контекст!!!

@RequiredArgsConstructor
public class MultiplexerServiceIT extends IntegrationTestBase{


    final MultiplexerService multiplexerService; // инжектим наш сервис
    private static final Integer MUX_ID = 10;

    // Можно заинжектить какой-то mock или spy. Тогда вызовется 2 спринг контекста, т.к. контексты снова отличаются
    // Или указать его в классе с @TestConfiguration, тогда он буден доступен всем
    // @SpyBean(name = "pool1")
    // private ConnectionPool pool1;


    @Test
    void findAll() {

        // Объект сортровки по name c DESC
        Sort sortObj = Sort.by(Sort.Direction.fromString("DESC"), "name");
        // Создадим объект Pageable с учетом сортировки
        Pageable pageable = PageRequest.of(3, 10, sortObj);
        // Создадим фильтр
        MultiplexerFilter filter = new MultiplexerFilter("7", "", null);


        Page<MultiplexerReadDto> page = multiplexerService.findAll(filter, pageable);
        assertThat(page).isNotNull();
        assertThat(page).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(76);
        assertThat(page.getContent().get(4).getId()).isEqualTo(317);

    }

    @Test
    void findById() {
        Optional<MultiplexerShowDetailsDto> maybeMultiplexer = multiplexerService.findById(MUX_ID);
        assertTrue(maybeMultiplexer.isPresent());
        maybeMultiplexer.ifPresent(mux -> {
            assertEquals("Multiplexer 10", mux.getName());
            assertEquals(6, mux.getModules().size());
        });
    }


    @Test
    void create() {
        MultiplexerCreateEditDto muxDto = new MultiplexerCreateEditDto(
                "testName",
                "123456",
                3
        );
        MultiplexerReadDto actualResult = multiplexerService.create(muxDto);

        assertEquals(muxDto.getName(), actualResult.getName());
        assertEquals(muxDto.getSerialNumber(), actualResult.getSerialNumber());
        assertEquals(muxDto.getSiteId(), actualResult.getSite().id());

    }


    @Test
    void update() {
        MultiplexerCreateEditDto expectedDto = new MultiplexerCreateEditDto(
                "testName",
                "123456",
                3
        );

        Optional<MultiplexerReadDto> actualResult = multiplexerService.update(MUX_ID, expectedDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(mux -> {
            assertEquals(expectedDto.getName(), mux.getName());
            assertEquals(expectedDto.getSerialNumber(), mux.getSerialNumber());
            assertEquals(expectedDto.getSiteId(), mux.getSite().id());

        });
    }

    @Test
    void delete() {
        assertFalse(multiplexerService.delete(-124));
        assertTrue(multiplexerService.delete(MUX_ID));
    }


}
