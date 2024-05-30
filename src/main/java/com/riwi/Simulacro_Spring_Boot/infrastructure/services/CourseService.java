package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.CourseReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ICourseService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService{

    // Inyeccion de dependencias user
    @Autowired
    private final UserRepository userRepository;

    // Inyeccion de dependencias Curso
    @Autowired
    private final CourseRepository courseRepository;

    // Crear
    @Override
    public CourseResp create(CourseReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    // Obtener solo uno por id
    @Override
    public CourseResp get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    // Actualizar
    @Override
    public CourseResp update(CourseReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    // ELiminar
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // Obtener todo
    @Override
    public Page<CourseResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page, size);

        return this.courseRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Buscar por nombre
    @Override
    public UserResp findByName(String courseName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }


    // Metodos privados
    
    // Convertir
    private CourseResp entityToResponse(Course entity) {

        UserBasicResp userResp = new UserBasicResp();
        BeanUtils.copyProperties(entity.getUserEntity(), userResp);

        return CourseResp.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .userEntity(userResp)
                .build();
    }
}