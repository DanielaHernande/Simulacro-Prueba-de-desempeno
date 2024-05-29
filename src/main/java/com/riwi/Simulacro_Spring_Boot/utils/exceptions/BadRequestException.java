package com.riwi.Simulacro_Spring_Boot.utils.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
    
}
