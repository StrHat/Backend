package com.konkuk.strhat.global.config;

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
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("AccessToken", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                ))
                .addSecurityItem(new SecurityRequirement()
                        .addList("AccessToken"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("StrHat API")
                .description("스틀햇 API 문서")
                .version("1.0.0"); // API 버전
    }
}