package com.fomich.netdata.config;

import com.fomich.netdata.database.entity.Role;
import com.fomich.netdata.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity // чтобы работали security аннотации над методами (контроллеров)
@RequiredArgsConstructor
public class SecurityConfiguration{

    private final UserService userService;

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

                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .defaultSuccessUrl("/multiplexers")
                                .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())) // Он отвечает за создание объекта authentication (изменим его Чтобы совместить существующих пользователей с Oauth). Нужно предоставить свою реализацию oidcUserService
                );
                //.httpBasic(withDefaults());
        return http.build(); // возвращает securityFilterChain объект
    }



    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() { // OAuth2UserService<OidcUserRequest, OidcUser> это функциональный интерфейс который принимает OidcUserRequest и возвращает OidcUser
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email"); //idToken это JWT токен, все значения уже распаршены. Передачу email мы настроили в scope провайдера: просили openid, email, profile
            // сайт jwt.io позволяет посмотреть что в этом токене
            // userRequest.getAccessToken() есть также access token от oauth провайдера с ролями, но мы эти роли не используем

            // TODO: create user userService.create
            UserDetails userDetails = userService.loadUserByUsername(email); // достанем пользователя из БД
//            new OidcUserService().loadUser()
            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken()); // создадим нового oidc user с нашими authorities

            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

            // Вернем proxy который если мы обращаемся к методу userDetails возвращает данные userDetails объекта, если обращаемся к методу oidcUser то из oidcUser объекта
            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class}, // набор интерфейсов
                    (proxy, method, args) -> userDetailsMethods.contains(method) // если запрашиваемый метод есть в userDetailsMethods
                            ? method.invoke(userDetails, args) // тогда вызываем у userDetails
                            : method.invoke(oidcUser, args)); // иначе у oidcUser
        };
    }








    /*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }

     */

}
