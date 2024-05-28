package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResp {
    
    private Long id;
    private String lessonTitle;
    private String content;

    private CourseBasicResp course;
}
