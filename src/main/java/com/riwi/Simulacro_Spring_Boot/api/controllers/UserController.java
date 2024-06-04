package com.riwi.Simulacro_Spring_Boot.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@Tag(name = "Users")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private final IUserService userService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = "Get users",
        description = "Get all users"
    )
    public ResponseEntity<Page<UserResp>> getll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
        ) {
        return ResponseEntity.ok(this.userService.getAll(page - 1, size));
    }

    // Buscar por id
    @GetMapping(path = "/{user_id}")
    @Operation(
        summary = "Search for a user by id",
        description = "Obtain detailed information about a specific user."
    )
    public ResponseEntity<UserResp> getId(
            @PathVariable Long user_id) {

        return ResponseEntity.ok(this.userService.get(user_id));
    }

    // Crear
    @PostMapping
    @Operation(
        summary = "Create users",
        description = "Create users with different roles"
    )
    public ResponseEntity<UserResp> create(
            @Validated @RequestBody UserReq request) {

        return ResponseEntity.ok(this.userService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{user_id}")
    @Operation(
        summary = "Update a user", 
        description = "Update a user's information."
    )
    public ResponseEntity<UserResp> update(
            @Validated @RequestBody UserReq request,
            @PathVariable Long user_id) {

        return ResponseEntity.ok(this.userService.update(request, user_id));
    }

    // Eliminar
    @DeleteMapping(path = "/{user_id}")
    @Operation(
        summary = "Delete User",
        description = "Delete a user by id."
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long user_id) {

        this.userService.delete(user_id);
        return ResponseEntity.noContent().build();
    }
}
