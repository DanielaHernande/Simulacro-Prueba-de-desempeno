package com.riwi.Simulacro_Spring_Boot.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

// Configuracion de swagger
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Api para administrar cursos",
        version = "1.0",
        description = "Documentación api para administrar cursos"))
public class OpenApiConfig {

}
