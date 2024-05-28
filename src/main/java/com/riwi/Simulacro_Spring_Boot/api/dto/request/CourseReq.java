package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReq {
    
    @NotBlank(message = "El nombre del curso es requerido")
    @Size(
        min = 10,
        max = 100,
        message = "El nombre del curso debe tener entre 10 y 50 caracteres"
    )
    private String courseName;
    
    private String description;

    // User
    @NotNull(message = "El id del usuerio es obligatorio")
    @Min(value = 1, message = "El id del usuario es requerido")
    private Long userId;
}
