package com.riwi.Simulacro_Spring_Boot.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserSecurity;

public interface UserSecurityRepository extends JpaRepository<UserSecurity,String>{

    public Optional<UserEntity> findByUserName(String username);
    
}
