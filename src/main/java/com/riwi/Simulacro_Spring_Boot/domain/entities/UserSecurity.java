package com.riwi.Simulacro_Spring_Boot.domain.entities;

import com.riwi.Simulacro_Spring_Boot.utils.enums.RoleAuth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 150, nullable = false, unique = true)
    private String userName;

    @Column(length = 150, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleAuth role;

    
}