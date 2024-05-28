package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.DecimalMin;
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
public class SubmissionReq {
    
    private String content;

    @FutureOrPresent(message = "La fecha del envio debe de ser en presente o futuro")
    private Date submissionDate;

    @NotNull(message = "EL grado es requerido")
    @DecimalMin(
        value = "0.01",
        message = "El grado del envio debe ser mayor a 0"
    )
    private BigDecimal grade;

    @NotNull(message = "El id del usuario es obligatorio")
    @Min(value = 1, message = "El id del usuario es requerido")
    private Long userId;

    @NotNull(message = "El id de la tarea es obligatorio")
    @Min(value = 1, message = "El id de la tarea es requerido")
    private Long assignmentId;
}
