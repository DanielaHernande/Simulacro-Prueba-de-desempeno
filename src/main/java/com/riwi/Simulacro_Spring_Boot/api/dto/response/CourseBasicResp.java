package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseBasicResp {
    
    private Long id;
    private String courseName;
    private String description;
}
