package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LoginReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.request.RegisterReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserRegisterReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AuthResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserSecurity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserSecurityRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IAuthService;
import com.riwi.Simulacro_Spring_Boot.infrastructure.helpers.JwtService;
import com.riwi.Simulacro_Spring_Boot.utils.enums.RoleAuth;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService{

    // Inyeccion de dependencias
    @Autowired
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResp login(LoginReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public AuthResp register(RegisterReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public AuthResp userRegister(UserRegisterReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userRegister'");
    }

    // Metodo privado

    //private UserSecurity findUser(String username){

    //    return this.userSecurityRepository.findByUserName(username)
    //            .orElse(null);
    //}

   // private UserSecurity validateUser(String username, String password, RoleAuth role){

        /*1. Validar que el usuario no existe */
//UserSecurity exist = this.findUser(username);

    //    if (exist != null) {
    //        throw new BadRequestException("El usuario ya existe");
  //      }
//
      //    /*Construir el usuario */
      //    return UserSecurity.builder()
     //           .userName(username)
      //          .password(this.passwordEncoder.encode(password))
      //          .role(role)
     //           .build();
   // }
    
}
