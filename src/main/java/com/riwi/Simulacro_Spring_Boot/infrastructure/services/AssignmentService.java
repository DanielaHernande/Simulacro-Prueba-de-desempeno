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

        // Convertir el DTO a una entidad Assignment
        Assignment assignment = this.requestToEntity(request);

        // Asignar la lección a la tarea
        assignment.setLesson(lesson);

        // Guardar la tarea en el repositorio de asignaciones
        return this.entityToResponse(this.assignmentRepository.save(assignment));
    }

    // Obtener solo uno
    @Override
    public AssignmentResp get(Long id) {
        
        // Buscar y devolver la tarea por su ID
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public AssignmentResp update(AssignmentReq request, Long id) {

        // Obtener la tarea por su ID
        Assignment assignment = this.find(id);

        // Obtener la lección por su ID
        Lesson lesson = this.lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new BadRequestException("No hay una leccion con ese id suministrado"));

        
        // Actualizar los campos de la tarea con los datos del DTO
        assignment.setAssignmentTitle(request.getAssignmentTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDueDate(request.getDueDate());
        assignment.setLesson(lesson);

        // Guardar los cambios en el repositorio de asignaciones
        return this.entityToResponse(this.assignmentRepository.save(assignment));
    }

    // Eliminar
    @Override
    public void delete(Long id) {
        
        // Eliminar la tarea por su ID
        this.assignmentRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<AssignmentResp> getAll(int page, int size) {

        // Validar la página para asegurarse de que no sea negativa
        if (page < 0) page = 1;

        // Configurar la paginación para la consulta
        PageRequest pagination = PageRequest.of(page - 1, size);

        // Obtener todas las tareas con paginación y convertirlas a DTOs
        return this.assignmentRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    
    // Convertir entidad Assignment a DTO AssignmentResp
    private AssignmentResp entityToResponse(Assignment entity) {

        Lesson lesson = new Lesson();

        // Verificar si la lección no es nula y copiar propiedades si existe
        if (entity.getLesson() != null) {
            BeanUtils.copyProperties(entity.getLesson(), lesson);
        }

        // Crear y retornar un DTO AssignmentResp
        return AssignmentResp.builder()
                .id(entity.getId())
                .assignmentTitle(entity.getAssignmentTitle())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .build();
    }
    
    // Convertir DTO AssignmentReq a entidad Assignment
    private Assignment requestToEntity(AssignmentReq requets) {

        // Crear y retornar una entidad Assignment a partir de un DTO AssignmentReq
        return Assignment.builder()
                .assignmentTitle(requets.getAssignmentTitle())
                .description(requets.getDescription())
                .build();
    }

    // Buscar por id
    private Assignment find(Long id) {

        // Buscar y retornar una tarea por su ID, o lanzar una excepción BadRequestException si no se encuentra
        return this.assignmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay una tarea con el id suministrado"));
    }
}

