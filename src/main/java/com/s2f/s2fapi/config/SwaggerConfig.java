package com.s2f.s2fapi.config;

import com.s2f.s2fapi.constants.ApiConstants; // Importez la classe de constantes
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(ApiConstants.BEARER_AUTH_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        ApiConstants.BEARER_AUTH_NAME,
                                        new SecurityScheme()
                                                .name(ApiConstants.BEARER_AUTH_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .bearerFormat(ApiConstants.BEARER_FORMAT)
                                                .in(SecurityScheme.In.HEADER)
                                                .scheme(ApiConstants.SECURITY_SCHEME_TYPE)))
                .info(
                        new Info()
                                .title(ApiConstants.API_TITLE)
                                .description(ApiConstants.API_DESCRIPTION)
                                .version(ApiConstants.API_VERSION));
    }
}
