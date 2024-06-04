package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import java.util.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentReq {
    
    @FutureOrPresent(message = "La fecha no debe ser del pasado")
    @NotNull(message = "La fecha de la inscripcion es requerida")
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
