package com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.MessageReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.MessageResp;

public interface IMessageService extends CrudService<MessageReq, MessageResp, Long>{
    
}
