package com.riwi.Simulacro_Spring_Boot.api.dto.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/*
 * Clase base para las respuestas de error.
 * Implementa Serializable para permitir la serialización del objeto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseErrorResp implements Serializable {
    
    private String status; // Estado HTTP de la respuesta
    private Integer code; // Código de error HTTP
}
