package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.UserReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
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

        UserEntity userEntity = this.requestToUser(request);

        return this.entityToResponse(this.userRepository.save(userEntity));
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

        response.setCourses(entity.getCourses().stream()
                .map(course -> this.courseToResponse(course))
                .collect(Collectors.toList()));

        return response;
    }

     // Para convertir CourseBasicResp a cursos
    private CourseBasicResp courseToResponse(Course entity) {

        return CourseBasicResp.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .build();
    }

    // 
    private UserEntity requestToUser(UserReq request) {

        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .role(request.getRole())
                .build();
    }

    private UserEntity findId(Long id) {

        return this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay usuarios con este id"));
    }
}
