package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.EnrollmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.EnrollmentResp;

public interface IEnrollmentsService extends CrudService<EnrollmentReq, EnrollmentResp, Long>{
    
}
