package com.riwi.Simulacro_Spring_Boot.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    
    @PostMapping(path = "/auth/login")
    public String login() {

        return "Desde Login";
    }

    @PostMapping(path = "/auth/register")
    public String register() {

        return "Desde Login";
    }
}
