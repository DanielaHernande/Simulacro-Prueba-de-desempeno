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
public class AssignmentReq {
    
    @NotBlank(message = "El titulo de la tarea es obligatorio")
    @Size(
        min = 10,
        max = 100,
        message = "El nombre de la tarea debe tener entre 10 y 100 caracteres"
    )
    private String assignmentTitle;

    private String description;

    // Lecciones
    @NotNull(message = "El id de la leccion es obligatorio")
    @Min(value = 1, message = "El id de la leccion es requerido")
    private Long lessonId;
}
