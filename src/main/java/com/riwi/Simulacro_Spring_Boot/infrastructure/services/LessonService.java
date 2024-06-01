package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LessonReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.LessonResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Lesson;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.LessonRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ILessonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService{

    // Dependencias curso
    @Autowired
    private final CourseRepository courseRepository;

    // Dependencias lesson
    @Autowired
    private final LessonRepository lessonRepository;

    // Create
    @Override
    public LessonResp create(LessonReq request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    // Obtener solo uno
    @Override
    public LessonResp get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    // Actualizar 
    @Override
    public LessonResp update(LessonReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    // Eliminar
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // Obtener todas las lesson
    @Override
    public Page<LessonResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page, size);

        return this.lessonRepository.findAll(pagination)
                .map(this::entityToResponse);
    }


    // Metodos privados
    
    // Convertir
    private LessonResp entityToResponse(Lesson entity) {

        CourseBasicResp course = new CourseBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getLessonTitle() != null) {
            BeanUtils.copyProperties(entity.getCourse(), course);
        }

        return LessonResp.builder()
                .id(entity.getId())
                .lessonTitle(entity.getLessonTitle())
                .content(entity.getContent())
                .course(course)
                .build();
    }
    
}
