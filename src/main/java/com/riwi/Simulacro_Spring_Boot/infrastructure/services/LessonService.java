package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.LessonReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.LessonResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Lesson;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.LessonRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ILessonService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

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
        
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No hay cursos con ese id"));

        Lesson lesson = this.requestToEntity(request);

        lesson.setCourse(course);

        return this.entityToResponse(this.lessonRepository.save(lesson));
    }

    // Obtener solo uno
    @Override
    public LessonResp get(Long id) {

        return this.entityToResponse(find(id));
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

    private Lesson requestToEntity(LessonReq requets) {

        return Lesson.builder()
                .lessonTitle(requets.getLessonTitle())
                .content(requets.getContent())
                .build();
    }

    // Buscar por id
    private Lesson find(Long id) {

        return this.lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay lesson con el id suministrado"));
    }
    
}
