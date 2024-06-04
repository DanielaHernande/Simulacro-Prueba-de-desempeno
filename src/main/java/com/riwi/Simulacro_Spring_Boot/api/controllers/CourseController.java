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

import com.riwi.Simulacro_Spring_Boot.api.dto.request.CourseReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseResp;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ICourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Courses")
@RequestMapping(path = "/courses")
public class CourseController {

    // Inyeccion de dependencia 
    @Autowired
    private final ICourseService courseService;

    // Obtener todo
    @GetMapping
    @Operation(
        summary = "Get All Courses",
        description = "Get a list of all courses."
    )
    public ResponseEntity<Page<CourseResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.courseService.getAll(page, size));
    }

    // Obtener por id
    @GetMapping(path = "/{id}")
    @Operation(
        summary = "Get Course Information",
        description = "Obtain detailed information about a specific course."
    )
    public ResponseEntity<CourseResp> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(this.courseService.get(id));
    }

    // Crear 
    @PostMapping
    @Operation(
        summary = "Create Course",
        description = "Create a new course."
    )
    public ResponseEntity<CourseResp> create(
            @Validated @RequestBody CourseReq request) {

        return ResponseEntity.ok(this.courseService.create(request));
    }

    // Actualizar
    @PutMapping(path = "/{id}")
    @Operation(
        summary = "Update Course",
        description = "Update the information of a course."
    )
    public ResponseEntity<CourseResp> update(
            @Validated @RequestBody CourseReq request,
            @PathVariable Long id) {

        return ResponseEntity.ok(this.courseService.update(request, id));
    }

    // Eliminar 
    @DeleteMapping(path = "/{id}")
    @Operation(
        summary = "Delete Course",
        description = "Delete a course."
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        this.courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
