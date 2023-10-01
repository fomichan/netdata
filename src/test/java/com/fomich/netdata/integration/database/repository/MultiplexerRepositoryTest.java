package com.fomich.netdata.integration.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.integration.IntegrationTestBase;
import com.fomich.netdata.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@IT // есть в IntegrationTestBase
@RequiredArgsConstructor
//@Rollback // Стоит по умолчанию, тк не следует сохранять что сделали в тесте
public class MultiplexerRepositoryTest extends IntegrationTestBase {




    private static final Integer MUX_ID = 2;

    private final MultiplexerRepository multiplexerRepository;
    private final SiteRepository siteRepository;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate; // Для настройки транзакции и изменения значений по умолчанию

    // !!!!! Чтобы инжектились бины необходимо в файле spring.properties указать spring.test.constructor.autowire.mode=all



    @Test
    void checkJdbcTemplate() {
        var mux = multiplexerRepository.findAllBySiteId(3);
        assertThat(mux).hasSize(2);
    }


//    @Test
//    @Commit // чтобы не было rollback и можно посмотреть что создал enverse
//    void checkAuditing() {
//        var mux = multiplexerRepository.findById(2).get();
//        mux.setName(mux.getName() + "xx");
//        multiplexerRepository.flush();
//        System.out.println();
//    }

    @Test
    void checkCustomImplementation() {
        MultiplexerFilter filter = new MultiplexerFilter("%ux1%", null);
        var muxes = multiplexerRepository.findAllByFilter(filter);
        assertThat(muxes).hasSize(5);
    }


    @Test
    void checkProjections() {
        var multiplexers = multiplexerRepository.findAllBySiteId(2);
        assertThat(multiplexers).hasSize(2);
    }

    @Test
    void checkPageble() {

        var pageable = PageRequest.of(0, 2, Sort.by("id"));
        var slice = multiplexerRepository.findAllBy(pageable);
        slice.forEach(mux -> System.out.println(mux.getId()));

        while (slice.hasNext()) {
            slice = multiplexerRepository.findAllBy(slice.nextPageable());
            slice.forEach(mux -> System.out.println(mux.getId()));
        }
    }



    @Test
    void checkFindByQueries() {
        multiplexerRepository.findByName("s2MUX1"); // сработает @NamedQuery тк он приоритетнее
        multiplexerRepository.findAllByNameContainingIgnoreCase("ux1");
    }


    @Test
    void checkUpdate() {
        int resultCount = multiplexerRepository.updateSite(siteRepository.findById(4).get(), 4, 5);
        assertEquals(2, resultCount);

    }

//    @Test
//    void delete() {
//        var maybeMultiplexer = multiplexerRepository.findById(MUX_ID);
//        assertTrue(maybeMultiplexer.isPresent());
//        maybeMultiplexer.ifPresent(multiplexerRepository::delete);
//        entityManager.flush(); // тк commit не произойдет то закрытия транзакции (Lazy)
//        assertTrue(multiplexerRepository.findById(MUX_ID).isEmpty());
//    }

    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> { // transactionTemplate используется когда нужно ручное управление транзакцией
            var mux = entityManager.find(Multiplexer.class, 1);
            assertNotNull(mux);
            assertThat(mux.getMultiplexerChannels()).hasSize(2);
        });
    }
}
