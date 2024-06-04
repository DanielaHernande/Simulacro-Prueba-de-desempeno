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
        
        // Buscar el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No hay cursos con ese id"));

        // Convertir la solicitud a entidad Lesson
        Lesson lesson = this.requestToEntity(request);

        // Asignar el curso a la lección
        lesson.setCourse(course);

        // Guardar la lección en el repositorio y devolver la respuesta
        return this.entityToResponse(this.lessonRepository.save(lesson));
    }

    // Obtener solo uno
    @Override
    public LessonResp get(Long id) {

        // Buscar la lección y convertirla a respuesta
        return this.entityToResponse(find(id));
    }

    // Actualizar 
    @Override
    public LessonResp update(LessonReq request, Long id) {

        // Buscar la lección existente
        Lesson lesson = this.find(id);

        // Buscar el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
            .orElseThrow(() -> new BadRequestException("No hay un curso con ese id suministrado"));


        // Actualizar el título, contenido y curso de la lección
        lesson.setLessonTitle(request.getLessonTitle());
        lesson.setContent(request.getContent());
        lesson.setCourse(course);

        // Guardar la lección actualizada en el repositorio y devolver la respuesta
        return this.entityToResponse(this.lessonRepository.save(lesson));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        // Eliminar la lección del repositorio
        this.lessonRepository.delete(this.find(id));
    }

    // Obtener todas las lesson
    @Override
    public Page<LessonResp> getAll(int page, int size) {

        // Ajustar el número de página si es necesario
        if (page < 0) page = 1;

        // Crear objeto de paginación
        PageRequest pagination = PageRequest.of(page, size);

        // Obtener todas las lecciones paginadas y convertirlas a respuestas
        return this.lessonRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    
    // Convertir entidad Lesson a respuesta LessonResp
    private LessonResp entityToResponse(Lesson entity) {

        CourseBasicResp course = new CourseBasicResp();

        // Copiar propiedades del curso si no es nulo
        if (entity.getLessonTitle() != null) {
            BeanUtils.copyProperties(entity.getCourse(), course);
        }

        // Construir y devolver la respuesta
        return LessonResp.builder()
                .id(entity.getId())
                .lessonTitle(entity.getLessonTitle())
                .content(entity.getContent())
                .course(course)
                .build();
    }

    // Convertir solicitud LessonReq a entidad Lesson
    private Lesson requestToEntity(LessonReq requets) {

        return Lesson.builder()
                .lessonTitle(requets.getLessonTitle())
                .content(requets.getContent())
                .build();
    }

    // Buscar por id
    private Lesson find(Long id) {

        // Buscar lección por ID y lanzar excepción si no se encuentra
        return this.lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay lesson con el id suministrado"));
    }
}
