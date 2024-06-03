package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import com.riwi.Simulacro_Spring_Boot.utils.enums.Role;

import jakarta.validation.constraints.Email;
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
public class UserRegisterReq {
    
        @NotBlank(message = "El nombre es requerido")
    @Size(
        min = 10,
        max = 50,
        message = "El nombre debe tener entre 10 y 50 caracteres"
    )
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    @Size(
        min = 10,
        max = 255,
        message = "La contraseña debe tener entre 10 y 100 caracteres"
    )
    private String password;

    @Email
    private String email;

    @NotBlank(message = "El nombre completo es requerido")
    @Size(
        min = 10,
        max = 100,
        message = "El nombre completo debe tener entre 10 y 100 caracteres"
    )
    private String fullName;

    @NotNull(message = "El Rol es requerido")
    private Role role;
}
