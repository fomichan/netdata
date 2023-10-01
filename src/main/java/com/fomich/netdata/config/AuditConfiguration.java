package com.fomich.netdata.config;

import com.fomich.netdata.NetdataApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = NetdataApplication.class)
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContext.getCurrentUser().getEmail()
        return () -> Optional.of("fomich");
    }
}
