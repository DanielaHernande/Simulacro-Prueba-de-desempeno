package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResp {
    
    private Long id;
    private String messageContent;
    private Date sentDate;

    // usuarioRemitente
    private UserBasicResp userSender;

    // usuarioReceptor
    private UserBasicResp userReceiver;

    // Curso
    private CourseBasicResp course;

}
