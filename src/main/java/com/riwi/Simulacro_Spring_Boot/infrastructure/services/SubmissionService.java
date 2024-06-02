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

        // 
        UserEntity user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("No hay usuarios con ese id"));

        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));

        Submission submission = this.requestToEntity(request);

        submission.setUserEntity(user);
        submission.setAssignment(assignment);

        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    // Obtener solo uno
    @Override
    public SubmissionResp get(Long id) {

        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public SubmissionResp update(SubmissionReq request, Long id) {

        Submission submission = this.find(id);

        // Obtener el usuario
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));
        
        Assignment assignment = this.assignmentRepository.findById(request.getAssignmentId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));

        submission = this.requestToEntity(request);


        submission.setUserEntity(user);
        submission.setAssignment(assignment);

        return this.entityToResponse(this.submissionRepository.save(submission));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        this.submissionRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<SubmissionResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page - 1, size);

        return this.submissionRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    // Convertir
    private SubmissionResp entityToResponse(Submission entity) {

        UserBasicResp user = new UserBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getUserEntity() != null) {
            BeanUtils.copyProperties(entity.getUserEntity(), user);
        }

        AssignmentResp assignment = new AssignmentResp();

        // Validar que el usuario es nulo 
        if (entity.getAssignment() != null) {
            BeanUtils.copyProperties(entity.getAssignment(), user);
        }

        return SubmissionResp.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .submissionDate(entity.getSubmissionDate())
                .grade(entity.getGrade())
                .assignment(assignment)
                .userEntity(user)
                .build();
    }
    
    private Submission requestToEntity(SubmissionReq requets) {

        return Submission.builder()
                .content(requets.getContent())
                .submissionDate(requets.getSubmissionDate())
                .grade(requets.getGrade())
                .build();
    }

    // Buscar por id
    private Submission find(Long id) {

        return this.submissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay cursos con el id suministrado"));
    }
    
}
