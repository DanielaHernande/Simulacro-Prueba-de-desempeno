package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.AssignmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AssignmentResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Assignment;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Lesson;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.AssignmentRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.LessonRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IAssignmentService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssignmentService implements IAssignmentService{

    // Dependencias lesson
    @Autowired
    private final LessonRepository lessonRepository;

    // Dependencias tareas
    @Autowired
    private final AssignmentRepository assignmentRepository;

    // Crear
    @Override
    public AssignmentResp create(AssignmentReq request) {

        // btener la lección
        Lesson lesson = this.lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new BadRequestException("No hay una leccion con ese id"));

        // tarea
        Assignment assignment = this.requestToEntity(request);

        // Asignar la lección a la tarea
        assignment.setLesson(lesson);

        // Guardar la tarea en el repositorio de asignaciones
        return this.entityToResponse(this.assignmentRepository.save(assignment));
    }

    // Obtener solo uno
    @Override
    public AssignmentResp get(Long id) {
        
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public AssignmentResp update(AssignmentReq request, Long id) {

        Assignment assignment = this.find(id);

        // Obtener el usuario
        Lesson lesson = this.lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new BadRequestException("No hay una leccion con ese id suministrado"));

        assignment = this.requestToEntity(request);

        assignment.setLesson(lesson);

        return this.entityToResponse(this.assignmentRepository.save(assignment));
    }

    // Eliminar
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // Obtener todo
    @Override
    public Page<AssignmentResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page - 1, size);

        return this.assignmentRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    
    // Convertir
    private AssignmentResp entityToResponse(Assignment entity) {

        Lesson lesson = new Lesson();

        // Validar que el usuario es nulo 
        if (entity.getLesson() != null) {
            BeanUtils.copyProperties(entity.getLesson(), lesson);
        }

        return AssignmentResp.builder()
                .id(entity.getId())
                .assignmentTitle(entity.getAssignmentTitle())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .build();
    }
    
    private Assignment requestToEntity(AssignmentReq requets) {

        return Assignment.builder()
                .assignmentTitle(requets.getAssignmentTitle())
                .description(requets.getDescription())
                .build();
    }

    // Buscar por id
    private Assignment find(Long id) {

        return this.assignmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay una tarea con el id suministrado"));
    }
}

