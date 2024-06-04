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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Lessons")
@RequestMapping(path = "/lessons")
public class LessonController {

    // Inyeccion de dependencias
    @Autowired
    private final ILessonService lessonService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = " Get All Lessons",
        description = "Get all lessons"
    )
    public ResponseEntity<Page<LessonResp>> getAll(

            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(this.lessonService.getAll(page - 1, size));
    }

    // Obtener uno solo
    @GetMapping(path = "/{lesson_id}")
    @Operation(
        summary = "Get Lesson Information",
        description = "Obtain detailed information about a specific lesson."
    )
    public ResponseEntity<LessonResp> get(
            @PathVariable Long lesson_id) {

        return ResponseEntity.ok(this.lessonService.get(lesson_id));
    }

    // Crear 
    @PostMapping
    @Operation(
        summary = "Create Lesson",
        description = "Create a new lesson."
    )
    public ResponseEntity<LessonResp> create(
            @Validated @RequestBody LessonReq request) {

        return ResponseEntity.ok(this.lessonService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{lesson_id}")
    @Operation(
        summary = "Update Lesson",
        description = "Update the information of a lesson."
    )
    public ResponseEntity<LessonResp> update(
            @Validated @RequestBody LessonReq request,
            @PathVariable Long lesson_id) {

        return ResponseEntity.ok(this.lessonService.update(request, lesson_id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{lesson_id}")
    @Operation(
        summary = "Delete Lesson",
        description = "Update the information of a lesson."
    )
    public ResponseEntity<Void> delete(@PathVariable Long lesson_id) {
        
        this.lessonService.delete(lesson_id);
        return ResponseEntity.noContent().build();
    }
    
}
