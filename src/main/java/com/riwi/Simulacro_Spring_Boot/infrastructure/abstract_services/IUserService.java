package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;

public interface IUserService extends CrudService<UserReq, UserResp, Long>{
    
    public UserResp getById(Long id);
}
