package com.riwi.Simulacro_Spring_Boot.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IUserService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private final IUserService userService;

    // Obtener todo
    @GetMapping
    public ResponseEntity<Page<UserResp>> getll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
        ) {
        return ResponseEntity.ok(this.userService.getAll(page - 1, size));
    }

    // Buscar por id
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResp> getId(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.userService.get(id));
    }

    // Crear
    @PostMapping
    public ResponseEntity<UserResp> create(
            @Validated @RequestBody UserReq request) {

        return ResponseEntity.ok(this.userService.create(request));
    }

    // Eliminar
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
