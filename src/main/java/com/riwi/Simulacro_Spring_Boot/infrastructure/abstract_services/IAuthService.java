package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LoginReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.request.RegisterReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserRegisterReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AuthResp;

public interface IAuthService {
    
    public AuthResp login(LoginReq request);
    public AuthResp register(RegisterReq request);
    public AuthResp userRegister(UserRegisterReq request);
}
