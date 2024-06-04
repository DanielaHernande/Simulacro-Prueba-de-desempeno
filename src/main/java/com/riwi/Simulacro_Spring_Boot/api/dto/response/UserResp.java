package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import java.util.List;

import com.riwi.Simulacro_Spring_Boot.utils.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResp {
    
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Role role;

    // Cursos
    private List<CourseBasicResp> courses;

}
