package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.EnrollmentReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.EnrollmentResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserBasicResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Enrollment;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.EnrollmentRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IEnrollmentsService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentsService implements IEnrollmentsService{
    
    // Inyeccion de dependencias user
    @Autowired
    private final UserRepository userRepository;

    // Inyeccion de dependencias Curso
    @Autowired
    private final CourseRepository courseRepository;

    // Inyeccion de dependencias inscripciones
    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    // Crear
    @Override
    public EnrollmentResp create(EnrollmentReq request) {

        // Obtener el usuario por ID, lanzar excepción si no se encuentra
        UserEntity user = this.userRepository.findById(request.getUserId())
            .orElseThrow(() -> new BadRequestException("No hay usuarios con ese id"));

        // Obtener el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
            .orElseThrow(() -> new BadRequestException("No hay cursos con ese id"));

        // Convertir la solicitud a entidad Enrollment
        Enrollment enrollment = this.requestToEntity(request);

        // Asignar curso y usuario a la inscripción
        enrollment.setCourse(course);
        enrollment.setUserEntity(user);

        // Guardar la inscripción en el repositorio y devolver la respuesta
        return this.entityToResponse(this.enrollmentRepository.save(enrollment));
    }

    // Obtener solo uno
    @Override
    public EnrollmentResp get(Long id) {

        // Buscar la inscripción y convertirla a respuesta
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public EnrollmentResp update(EnrollmentReq request, Long id) {

        // Buscar la inscripción existente
        Enrollment enrollment = this.find(id);

         // Obtener el usuario por ID, lanzar excepción si no se encuentra
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No hay un usuario con ese id suministrado"));

        // Obtener el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No hay un curso con ese id suministrado"));

        // Actualizar la fecha de inscripción, curso y usuario
        enrollment.setEnrollmentDate(request.getEnrollmentDate());
        enrollment.setCourse(course);
        enrollment.setUserEntity(user);

        // Guardar la inscripción actualizada en el repositorio y devolver la respuesta
        return this.entityToResponse(this.enrollmentRepository.save(enrollment));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        // Eliminar la inscripción del repositorio
        this.enrollmentRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<EnrollmentResp> getAll(int page, int size) {

        // Ajustar el número de página si es necesario
        if (page < 0) page = 1;

        // Crear objeto de paginación
        PageRequest pagination = PageRequest.of(page - 1, size);

        // Obtener todas las inscripciones paginadas y convertirlas a respuestas
        return this.enrollmentRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    // Convertir entidad Enrollment a respuesta EnrollmentResp
    private EnrollmentResp entityToResponse(Enrollment entity) {

        UserBasicResp user = new UserBasicResp();

        // Copiar propiedades del usuario si no es nulo
        if (entity.getUserEntity() != null) {
            BeanUtils.copyProperties(entity.getUserEntity(), user);
        }

        CourseBasicResp course = new CourseBasicResp();

        // Copiar propiedades del curso si no es nulo
        if (entity.getCourse() != null) {
            BeanUtils.copyProperties(entity.getCourse(), course);
        }

        // Construir y devolver la respuesta
        return EnrollmentResp.builder()
                .id(entity.getId())
                .enrollmentDate(entity.getEnrollmentDate())
                .course(course)
                .userEntity(user)
                .build();
    }
    
     // Convertir solicitud EnrollmentReq a entidad Enrollment
    private Enrollment requestToEntity(EnrollmentReq request) {

        return Enrollment.builder()
                .enrollmentDate(request.getEnrollmentDate())
                .build();
    }

    // Buscar inscripción por ID y lanzar excepción si no se encuentra
    private Enrollment find(Long id) {

        return this.enrollmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay Inscripciones con el id suministrado"));
    }  
}
