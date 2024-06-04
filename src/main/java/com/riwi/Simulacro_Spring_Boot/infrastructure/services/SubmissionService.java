package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.SubmissionReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.AssignmentResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.SubmissionResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserBasicResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Assignment;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Submission;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.AssignmentRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.SubmissionRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.ISubmissionService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubmissionService implements ISubmissionService{

    // Inyeccion de dependencias user
    @Autowired
    private final UserRepository userRepository;

    // Inyeccion de dependencias 
    @Autowired
    private final AssignmentRepository assignmentRepository;

    @Autowired
    private final SubmissionRepository submissionRepository;
    
    // Crear
    @Override
    public SubmissionResp create(SubmissionReq request) {

        // Obtener el usuario por ID, lanzar excepción si no se encuentra
        UserEntity user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("No hay usuarios con ese id"));

        // Obtener la asignación por ID, lanzar excepción si no se encuentra
        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new BadRequestException("No ha un asignaciónes con ese id suministrado"));

        // Convertir la solicitud a entidad Submission
        Submission submission = this.requestToEntity(request);

        // Asignar el usuario y la asignación al envío
        submission.setUserEntity(user);
        submission.setAssignment(assignment);

        // Guardar el envío en el repositorio y devolver la respuesta
        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    // Obtener solo uno
    @Override
    public SubmissionResp get(Long id) {

        // Buscar el envío y convertirlo a respuesta
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public SubmissionResp update(SubmissionReq request, Long id) {

        // Buscar el envío existente por ID
        Submission submission = this.find(id);

        // Obtener el usuario por ID, lanzar excepción si no se encuentra
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));
        
        // Obtener la asignación por ID, lanzar excepción si no se encuentra
        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId())
                    .orElseThrow(() -> new BadRequestException("No ha un asignaciónes con ese id suministrado"));

        // Actualizar el contenido, fecha de envío, calificación, usuario y asignación del envío
        submission.setContent(request.getContent());
        submission.setSubmissionDate(request.getSubmissionDate());
        submission.setGrade(request.getGrade());
        submission.setUserEntity(user);
        submission.setAssignment(assignment);

        // Guardar el envío actualizado en el repositorio y devolver la respuesta
        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        // Eliminar el envío del repositorio
        this.submissionRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<SubmissionResp> getAll(int page, int size) {

        // Ajustar el número de página si es necesario
        if (page < 0) page = 1;

        // Crear objeto de paginación
        PageRequest pagination = PageRequest.of(page - 1, size);

        // Obtener todos los envíos paginados y convertirlos a respuestas
        return this.submissionRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    // Convertir entidad Submission a respuesta SubmissionResp
    private SubmissionResp entityToResponse(Submission entity) {

        // Crear objeto básico de respuesta para el usuario
        UserBasicResp user = new UserBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getUserEntity() != null) {
            BeanUtils.copyProperties(entity.getUserEntity(), user);
        }

        // Crear objeto básico de respuesta para la asignación
        AssignmentResp assignment = new AssignmentResp();

        // Validar que el usuario es nulo 
        if (entity.getAssignment() != null) {
            BeanUtils.copyProperties(entity.getAssignment(), assignment);
        }

        // Construir y devolver la respuesta del envío
        return SubmissionResp.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .submissionDate(entity.getSubmissionDate())
                .grade(entity.getGrade())
                .assignment(assignment)
                .userEntity(user)
                .build();
    }
    
    // Convertir solicitud SubmissionReq a entidad Submission
    private Submission requestToEntity(SubmissionReq requets) {

        return Submission.builder()
                .content(requets.getContent())
                .submissionDate(requets.getSubmissionDate())
                .grade(requets.getGrade())
                .build();
    }

    // Buscar por id
    private Submission find(Long id) {

        // Buscar envío por ID y lanzar excepción si no se encuentra
        return this.submissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay envíos con el id suministrado"));
    }
}
