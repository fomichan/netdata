package com.fomich.netdata.config;

import com.fomich.netdata.database.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity // чтобы работали security аннотации над методами (контроллеров)
public class SecurityConfiguration{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers("/login", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        //.requestMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.getAuthority())
                        //.requestMatchers("/users").hasAuthority(Role.MANAGER.getAuthority())
                        //.requestMatchers("/users/**").hasAuthority(Role.MANAGER.getAuthority())
                        .anyRequest().authenticated() //любое обращение к url только с authenticated пользователем
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // в случае успешного logout (по умолчанию так)
                        .deleteCookies("JSESSIONID") // удалить куки (по умолчанию так)
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/multiplexers", true) // если прошла успешно, идти туда Установите второй аргумент как true для метода GET
                        //.successForwardUrl("/multiplexers")
                        //.permitAll() // всем доступна страничка login
                );
                //.httpBasic(withDefaults());
        return http.build(); // возвращает securityFilterChain объект
    }




    // Создадим password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }

     */

}
