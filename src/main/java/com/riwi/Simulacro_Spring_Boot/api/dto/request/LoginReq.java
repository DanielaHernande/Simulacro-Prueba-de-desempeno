package com.riwi.Simulacro_Spring_Boot.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 5, max = 150, message = "El usuario debe tener entre 8 y 150 caracteres")
    private String userName;

    @NotBlank(message = "La contraseña es obligatorio")
    @Size(min = 8, max = 150, message = "La contraseña debe tener entre 8 y 150 caracteres")
    private String password;
}
