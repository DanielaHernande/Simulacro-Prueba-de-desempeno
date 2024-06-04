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

import com.riwi.Simulacro_Spring_Boot.api.dto.request.SubmissionReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.SubmissionResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ISubmissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Submissions")
@RequestMapping(path = "/submission")
public class SubmissionController {
    
    // Inyeccion de dependencia 
    @Autowired
    private final ISubmissionService submissionService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = "Get All Deliveries ",
        description = "Get All Deliveries Get All Deliveries "
    )
    public ResponseEntity<Page<SubmissionResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.submissionService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{submission_id}")
    @Operation(
        summary = "Obtain Delivery Information",
        description = "Obtain detailed information on a specific delivery."
    )
    public ResponseEntity<SubmissionResp> get(
            @PathVariable Long submission_id) {

        return ResponseEntity.ok(this.submissionService.get(submission_id));
    }

    // Crear 
    @PostMapping
    @Operation(
        summary = "Create Delivery",
        description = "Create a new delivery for a task."
    )
    public ResponseEntity<SubmissionResp> create(
            @Validated @RequestBody SubmissionReq request) {

        return ResponseEntity.ok(this.submissionService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{submission_id}")
    @Operation(
        summary = "Update Delivery",
        description = "Update the information of a delivery."
    )
    public ResponseEntity<SubmissionResp> update(
            @Validated @RequestBody SubmissionReq request,
            @PathVariable Long submission_id) {

        return ResponseEntity.ok(this.submissionService.update(request, submission_id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{submission_id}")
    @Operation(
        summary = "Delete Delivery",
        description = "Delete a delivery."
    )
    public ResponseEntity<Void> delete(@PathVariable Long submission_id) {
        
        this.submissionService.delete(submission_id);
        return ResponseEntity.noContent().build();
    }
}
