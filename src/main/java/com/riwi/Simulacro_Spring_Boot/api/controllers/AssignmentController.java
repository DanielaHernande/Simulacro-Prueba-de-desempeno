package com.riwi.Simulacro_Spring_Boot.api.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.AssignmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AssignmentResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IAssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Assignment")
@RequestMapping(path = "/assignment")
public class AssignmentController {
    
        // Inyeccion de dependencia 
    @Autowired
    private final IAssignmentService assignmentService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = "Get All Tasks",
        description = "Get All Tasks"
    )
    public ResponseEntity<Page<AssignmentResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.assignmentService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{assignment_id}")
    @Operation(
        summary = "Obtain Task Information",
        description = "Obtener información detallada de una tarea específica."
    )
    public ResponseEntity<AssignmentResp> get(
            @PathVariable Long assignment_id) {

        return ResponseEntity.ok(this.assignmentService.get(assignment_id));
    }

    // Crear 
    @PostMapping
    @Operation(
        summary = "Create Task",
        description = "Create Task"
    )
    public ResponseEntity<AssignmentResp> create(
            @Validated @RequestBody AssignmentReq request) {

        return ResponseEntity.ok(this.assignmentService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{assignment_id}")
    @Operation(
        summary = "Update Task",
        description = "Update the information of a task."
    )
    public ResponseEntity<AssignmentResp> update(
            @Validated @RequestBody AssignmentReq request,
            @PathVariable Long assignment_id) {

        return ResponseEntity.ok(this.assignmentService.update(request, assignment_id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{assignment_id}")
    @Operation(
        summary = "Delete Task",
        description = "Delete a task."
    )
    public ResponseEntity<Void> delete(@PathVariable Long assignment_id) {
        
        this.assignmentService.delete(assignment_id);
        return ResponseEntity.noContent().build();
    }
}
