package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResp {
    
    private Long id;
    private String assignmentTitle;
    private String description;
    private Date dueDate;

    // User
    private UserBasicResp userEntity;

    // Envio
    private List<SubmissionBasicResp> submissions;
}
