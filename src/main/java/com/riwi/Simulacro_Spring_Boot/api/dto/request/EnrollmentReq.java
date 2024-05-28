package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import java.util.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnrollmentReq {
    
    @FutureOrPresent(message = "La fecha no debe ser del pasado")
    @NotBlank(message = "La fecha de la inscripcion es requerida")
    private Date enrollmentDate;

    // User
    @NotNull(message = "EL id del Estudiante/Profesor es obligatorio")
    @Min(value = 1, message = "El id del estudiante o profesor es requerido")
    private Long userId;

    // Curso
    @NotNull(message = "EL id del Estudiante/Profesor es obligatorio")
    @Min(value = 1, message = "El id del estudiante o profesor es requerido")
    private Long courseId;
}
