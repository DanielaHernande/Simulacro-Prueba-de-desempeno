package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.AssignmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AssignmentResp;

public interface IAssignmentService extends CrudService<AssignmentReq, AssignmentResp, Long>{
    
}
