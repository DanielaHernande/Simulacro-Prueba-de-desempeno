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

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResp  handleBadRequest(MethodArgumentNotValidException exception) {


        List<Map<String, String>> errors = new ArrayList<>();
        
        exception.getBindingResult().getFieldErrors().forEach(e -> {

            Map<String, String> error = new HashMap<>();

            error.put("error", e.getDefaultMessage());
            error.put("field", e.getField());

            errors.add(error);
        });

        return ErrorsResp.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .build();
    }
}
