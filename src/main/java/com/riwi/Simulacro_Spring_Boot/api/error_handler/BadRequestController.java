package com.riwi.Simulacro_Spring_Boot.api.error_handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.Simulacro_Spring_Boot.api.dto.errors.BaseErrorResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.errors.ErrorsResp;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

/**
 * Controlador para manejar excepciones específicas y retornar respuestas adecuadas.
 * Marcado con @RestControllerAdvice para aplicar consejos globales a los controladores.
 * Respuestas HTTP con estado 400 (BAD_REQUEST).
 */
@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {
    
    /**
    * Maneja excepciones de tipo MethodArgumentNotValidException.
    * @param exception la excepción capturada.
    * @return un objeto ErrorsResp con los detalles de los errores.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResp  handleBadRequest(MethodArgumentNotValidException exception) {


        List<Map<String, String>> errors = new ArrayList<>();
        
        // Procesa cada error de campo y agrega detalles al listado de errores
        exception.getBindingResult().getFieldErrors().forEach(e -> {

            Map<String, String> error = new HashMap<>();

            error.put("error", e.getDefaultMessage());
            error.put("field", e.getField());

            errors.add(error);
        });

        // Construye y retorna la respuesta de errores
        return ErrorsResp.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .build();
    }

    /**
    * Maneja excepciones de tipo BadRequestException.
    * @param exception la excepción capturada.
    * @return un objeto ErrorsResp con los detalles del error.
    */
    @ExceptionHandler(BadRequestException.class)
    public BaseErrorResp handleError(BadRequestException exception){

        List<Map<String,String>> errors = new ArrayList<>();

        Map<String,String> error = new HashMap<>();
        
        error.put("id", exception.getMessage());

        errors.add(error);

        // Construye y retorna la respuesta de errores
        return ErrorsResp.builder()
                .code(HttpStatus.BAD_REQUEST.value()) //400
                .status(HttpStatus.BAD_REQUEST.name()) //BAD_REQUEST
                .errors(errors) // [ { "field": "mal", "error": "mal"} ]
                .build();
    }
}
