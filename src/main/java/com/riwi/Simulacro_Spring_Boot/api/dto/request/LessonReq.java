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
public class LessonReq {
    
    @NotBlank(message = "EL titulo de la leccion es obligatorio")
    @Size(
        min = 3,
        max = 100,
        message = "EL titulo debe de tener entre 10y 100 caracteres"
    )
    private String lessonTitle;

    private String content;

    @NotNull(message = "El id del curso es obligatorio")
    @Min(value = 1, message = "El id del curso es requerido")
    private Long courseId;
}
