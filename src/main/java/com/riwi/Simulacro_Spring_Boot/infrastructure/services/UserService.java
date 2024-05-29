package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IUserService;
//import com.riwi.Simulacro_Spring_Boot.utils.enums.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    // Inyeccion de dependencia
    @Autowired
    private final UserRepository userRepository;

    // Crear
    @Override
    public UserResp create(UserReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    // Obtener solo uno
    @Override
    public UserResp get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    // Actualizar
    @Override
    public UserResp update(UserReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    // Eliminar
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // Obtener todo
    @Override
    public Page<UserResp> getAll(int page, int size) {

        if (page < 0) page = 0;

        PageRequest pagination = PageRequest.of(page, size);

        return this.userRepository.findAll(pagination)
                .map(user -> this.entityToResponse(user));     
    }

    // Obtener por id
    @Override
    public UserResp getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    // Metodos privados
    
    // Para convertir userResp a company
    private UserResp entityToResponse(UserEntity entity) {

        UserResp response = new UserResp();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
