package com.riwi.Simulacro_Spring_Boot.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

// Configuracion de swagger
@Configuration
@OpenAPIDefinition(
    info = @Info(
        
        contact = @Contact (

            name = "Daniela Jimenez Hernandez",
            url = "https://github.com/DanielaHernande",
            email = "danielajimenezhernandez6@gmail.com"
        ),

        title = "Api para administrar cursos",
        version = "1.0",
        description = "Documentación api para administrar cursos"))
public class OpenApiConfig {

    // ruta swagger http://localhost:8080/api/v1/swagger-ui/index.html
}
