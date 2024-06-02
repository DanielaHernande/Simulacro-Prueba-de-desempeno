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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/submission")
public class SubmissionController {
    
    // Inyeccion de dependencia 
    @Autowired
    private final ISubmissionService submissionService;

    // Obtener todo
    @GetMapping
    public ResponseEntity<Page<SubmissionResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.submissionService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubmissionResp> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.submissionService.get(id));
    }

    // Crear 
    @PostMapping
    public ResponseEntity<SubmissionResp> create(
            @Validated @RequestBody SubmissionReq request) {

        return ResponseEntity.ok(this.submissionService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{id}")
    public ResponseEntity<SubmissionResp> update(
            @Validated @RequestBody SubmissionReq request,
            @PathVariable Long id) {

        return ResponseEntity.ok(this.submissionService.update(request, id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        this.submissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
