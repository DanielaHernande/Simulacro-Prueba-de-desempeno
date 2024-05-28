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
public class MessageReq {

    private String messageContent;

    @FutureOrPresent(message = "La fecha del mensaje debe de ser a futuro")
    private Date sentDate;

    @NotNull(message = "El id del usuario Remitente es obligatorio")
    @Min(value = 1, message = "El id del usuario Remitente es requerido")
    private Long userSenderId;

    @NotNull(message = "El id del usuario Receptores obligatorio")
    @Min(value = 1, message = "El id del usuario Receptor es requerido")
    private Long userReceiverId;

    @NotNull(message = "El id del curso es obligatorio")
    @Min(value = 1, message = "El id del curso es requerido")
    private Long courseId;
}
