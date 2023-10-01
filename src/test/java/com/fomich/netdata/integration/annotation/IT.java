package com.fomich.netdata.integration.annotation;


import com.fomich.netdata.integration.TestNetdataApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test") // укажем тестовый профайл. В т.ч. будут добавлены или заменены properties из application-test.yml
@Transactional
@SpringBootTest(classes = TestNetdataApplication.class) // в ней уже включены @ExtendWith(SpringExtension.class) и @BootstrapWith(SpringBootTestContextBootstrapper.class)
public @interface IT {
}
