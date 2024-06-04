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

import com.riwi.Simulacro_Spring_Boot.api.dto.request.EnrollmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.EnrollmentResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IEnrollmentsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Enrollment")
@RequestMapping(path = "/enrollment")
public class EnrollmentController {

    // Inyeccion de dependencia 
    @Autowired
    private final IEnrollmentsService enrollmentsService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = "Get all enrollment",
        description = "Get all enrollment"
    )
    public ResponseEntity<Page<EnrollmentResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.enrollmentsService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{enrollment_id}")
    @Operation(
        summary = "Get Registration Information",
        description = "Obtain detailed information on a specific registration."
    )
    public ResponseEntity<EnrollmentResp> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.enrollmentsService.get(id));
    }

    // Crear 
    @PostMapping
    @Operation(
        summary = "Enroll User in Course",
        description = "Enroll a user in a course."
    )
    public ResponseEntity<EnrollmentResp> create(
            @Validated @RequestBody EnrollmentReq request) {

        return ResponseEntity.ok(this.enrollmentsService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{enrollment_id}")
    @Operation(
        summary = "Updating an enrollment",
        description = "Updating an enrollment"
    )
    public ResponseEntity<EnrollmentResp> update(
            @Validated @RequestBody EnrollmentReq request,
            @PathVariable Long enrollment_id) {

        return ResponseEntity.ok(this.enrollmentsService.update(request, enrollment_id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{enrollment_id}")
    @Operation(
        summary = "Delete an inscription.",
        description = "Delete an inscription."
    )
    public ResponseEntity<Void> delete(@PathVariable Long enrollment_id) {
        
        this.enrollmentsService.delete(enrollment_id);
        return ResponseEntity.noContent().build();
    }
    
}
