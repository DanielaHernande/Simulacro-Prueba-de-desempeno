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

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LessonReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.LessonResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ILessonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/lessons")
public class LessonController {

    // Inyeccion de dependencias
    @Autowired
    private final ILessonService lessonService;

    // Obtener todo
    @GetMapping
    public ResponseEntity<Page<LessonResp>> getAll(

            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(this.lessonService.getAll(page - 1, size));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LessonResp> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.lessonService.get(id));
    }

    // Crear 
    @PostMapping
    public ResponseEntity<LessonResp> create(
            @Validated @RequestBody LessonReq request) {

        return ResponseEntity.ok(this.lessonService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{id}")
    public ResponseEntity<LessonResp> update(
            @Validated @RequestBody LessonReq request,
            @PathVariable Long id) {

        return ResponseEntity.ok(this.lessonService.update(request, id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        this.lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
