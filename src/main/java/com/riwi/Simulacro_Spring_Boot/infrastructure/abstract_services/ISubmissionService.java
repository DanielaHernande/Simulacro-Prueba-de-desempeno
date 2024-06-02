package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.SubmissionReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.SubmissionResp;

public interface ISubmissionService extends CrudService<SubmissionReq, SubmissionResp, Long>{
    
}
