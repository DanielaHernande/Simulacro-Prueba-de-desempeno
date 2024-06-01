package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LessonReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.LessonResp;

public interface ILessonService extends CrudService<LessonReq, LessonResp, Long>{
    
}
