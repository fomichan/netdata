package com.fomich.netdata.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;



// Swagger configuration
@Configuration
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "https://netdata-db076adb964b.herokuapp.com/oauth2/authorization/google",
                        tokenUrl = "https://www.googleapis.com/oauth2/v4/token"
                )
        )
)
public class OpenApiConfiguration {
}
