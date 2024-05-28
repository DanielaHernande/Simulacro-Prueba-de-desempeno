package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResp {
    
    private Long id;
    private Date enrollmentDate;

    // User- Profesor
    private UserBasicResp userEntity;

    // Cursos
    private CourseBasicResp course;
}
