package com.riwi.Simulacro_Spring_Boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Activar Sprint Security
public class SecurityConfig {

    // Crear rutas publicas
    private final String[] PUBLIC_RESOURCES = { "/courses/public/get", "/auth/**" };

    /*@Bean esta anotacion le indica a Spring boot que el objeto retornado por 
     * el metodo debe ser retornado como un bean 
    */
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable()) // Desabilitar proteccion csrf
                .authorizeHttpRequests(authRequest -> authRequest
                    .requestMatchers(PUBLIC_RESOURCES).permitAll() // Configurar rutas publicas
                    .anyRequest().authenticated()
                ).build();
    }
}
