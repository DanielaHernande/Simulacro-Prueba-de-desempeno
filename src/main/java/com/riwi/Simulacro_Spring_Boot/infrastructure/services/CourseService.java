package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.CourseReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserBasicResp;
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

        // Obtener el usuario por su ID
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No hay usuarios con ese id"));

        // Convertir el DTO CourseReq a una entidad Course
        Course course = this.requestToEntity(request);

        // Asignar el usuario al curso
        course.setUserEntity(user);

        // Guardar el curso en el repositorio y devolver su representación DTO
        return this.entityToResponse(this.courseRepository.save(course));
    }

    // Obtener solo uno por id
    @Override
    public CourseResp get(Long id) {

        // Buscar y devolver el curso por su ID
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public CourseResp update(CourseReq request, Long id) {

        // Obtener el curso por su ID
        Course course = this.find(id);

        // Obtener el usuario
        UserEntity user = this.userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario con ese id suministrado"));

        // Actualizar los campos del curso con los datos del DTO
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setUserEntity(user);

        // Guardar los cambios en el repositorio y devolver la representación DTO del curso actualizado
        return this.entityToResponse(this.courseRepository.save(course));
    
    }

    // ELiminar
    @Override
    public void delete(Long id) {

        // Buscar y eliminar el curso por su ID
        this.courseRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<CourseResp> getAll(int page, int size) {

        // Asegurar que el número de página no sea negativo
        if (page < 0) page = 1;

        // Configurar la paginación para la consulta
        PageRequest pagination = PageRequest.of(page - 1, size);

        // Obtener todos los cursos con paginación y mapearlos a DTOs
        return this.courseRepository.findAll(pagination)
                .map(this::entityToResponse);
    }


    // Metodos privados
    // Convertir una entidad Course a un DTO CourseResp
    private CourseResp entityToResponse(Course entity) {

        UserBasicResp user = new UserBasicResp();

        // Verificar si el usuario no es nulo y copiar sus propiedades si existe
        if (entity.getUserEntity() != null) {
            BeanUtils.copyProperties(entity.getUserEntity(), user);
        }

        // Crear y retornar un DTO CourseResp
        return CourseResp.builder()
                .id(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .userEntity(user)
                .build();
    }
    
    // Convertir un DTO CourseReq a una entidad Course
    private Course requestToEntity(CourseReq requets) {

        // Crear y retornar una entidad Course a partir de un DTO CourseReq
        return Course.builder()
                .courseName(requets.getCourseName())
                .description(requets.getDescription())
                .build();
    }

    // Buscar por id
    private Course find(Long id) {

        // Buscar y retornar un curso por su ID, o lanzar una excepción BadRequestException si no se encuentra
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay cursos con el id suministrado"));
    }
}
