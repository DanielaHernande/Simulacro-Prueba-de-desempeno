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
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ICourseService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

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

        // 
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No hay usuarios con ese id"));

        // curso
        Course course = this.requestToEntity(request);

        course.setUserEntity(user);

        return this.entityToResponse(this.courseRepository.save(course));
    }

    // Obtener solo uno por id
    @Override
    public CourseResp get(Long id) {

        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public CourseResp update(CourseReq request, Long id) {

        Course course = this.find(id);

        // Obtener el usuario
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));

        course = this.requestToEntity(request);

        course.setUserEntity(user);

        return this.entityToResponse(this.courseRepository.save(course));
    
    }

    // ELiminar
    @Override
    public void delete(Long id) {

        this.courseRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<CourseResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page - 1, size);

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

        UserBasicResp user = new UserBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getUserEntity() != null) {
            BeanUtils.copyProperties(entity.getUserEntity(), user);
        }

        return CourseResp.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .userEntity(user)
                .build();
    }
    
    private Course requestToEntity(CourseReq requets) {

        return Course.builder()
                .courseName(requets.getCourseName())
                .description(requets.getDescription())
                .build();
    }

    // Buscar por id
    private Course find(Long id) {

        return this.courseRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay cursos con el id suministrado"));
    }
}
