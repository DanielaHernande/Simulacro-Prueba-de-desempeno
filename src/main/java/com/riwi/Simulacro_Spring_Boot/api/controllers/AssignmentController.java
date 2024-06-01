package com.riwi.Simulacro_Spring_Boot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacro_Spring_Boot.api.dto.response.AssignmentResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IAssignmentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/assignment")
public class AssignmentController {
    
        // Inyeccion de dependencia 
    @Autowired
    private final IAssignmentService assignmentService;

    // Obtener todo
    @GetMapping
    public ResponseEntity<Page<AssignmentResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.assignmentService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{id}")
    public ResponseEntity<AssignmentResp> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.assignmentService.get(id));
    }
}
