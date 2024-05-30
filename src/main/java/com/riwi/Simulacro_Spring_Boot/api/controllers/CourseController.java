package com.riwi.Simulacro_Spring_Boot.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.CourseReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ICourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/courses")
public class CourseController {

    // Inyeccion de dependencia 
    @Autowired
    private final ICourseService courseService;

    // Obtener todo
    @GetMapping
    public ResponseEntity<Page<CourseResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return ResponseEntity.ok(this.courseService.getAll(page, size));
    }

    // Crear 
    @PostMapping
    public ResponseEntity<CourseResp> create(
            @Validated @RequestBody CourseReq request) {

        return ResponseEntity.ok(this.courseService.create(request));
    }
}
