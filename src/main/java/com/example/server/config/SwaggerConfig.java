package com.example.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
//                .components(new Components()
//                        .addSchemas("User", new Schema<>()
//                                .type("object")
//                                        .addProperty("id", new Schema<>().type("integer"))
//                                        .addProperty("name", new Schema<>().type("string"))
//                        )
//                )
                .info(new Info()
                        .title("API приложения электронной библиотеки")
                        .version("1.0")
                        .description("Методы для работы с приложением электронной библиотеки")
                        .termsOfService("https://policies.google.com/terms?hl=ru-RU")
                        .contact(new Contact()
                                .name("Support")
                                .email("support@example.com")
                                .url("https://www.google.com/"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT License")
                                .url("https://opensource.org/license/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("External documentation")
                        .url("https://google.com"));
    }
}
