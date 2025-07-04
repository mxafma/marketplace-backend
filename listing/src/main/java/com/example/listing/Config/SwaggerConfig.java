package com.example.listing.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        .info(new Info()
            .title("Api 2025 Publicaciones (Listing)")
            .version("1.0")
            .description("Documentacion de la API para el Microservicio de Listing"));
    }
}
