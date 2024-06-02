package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.Simulacro_Spring_Boot.api.dto.request.MessageReq;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.CourseBasicResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.MessageResp;
import com.riwi.Simulacro_Spring_Boot.api.dto.response.UserBasicResp;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Course;
import com.riwi.Simulacro_Spring_Boot.domain.entities.Message;
import com.riwi.Simulacro_Spring_Boot.domain.entities.UserEntity;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.CourseRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.MessageRepository;
import com.riwi.Simulacro_Spring_Boot.domain.repositories.UserRepository;
import com.riwi.Simulacro_Spring_Boot.infrastructure.abstract_services.IMessageService;
import com.riwi.Simulacro_Spring_Boot.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService implements IMessageService{

    // Inyeccion de dependencias user
    @Autowired
    private final UserRepository userRepository;

    // Inyeccion de dependencias Curso
    @Autowired
    private final CourseRepository courseRepository;

    // Inyeccion de dependencias Curso
    @Autowired
    private final MessageRepository messageRepository;

    // Crear
    @Override
    public MessageResp create(MessageReq request) {

        // Obtener el usuario
        UserEntity userReceiver = this.userRepository.findById(request.getUserReceiverId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario remitente con ese id suministrado"));

        UserEntity userSender = this.userRepository.findById(request.getUserSenderId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario remitente con ese id suministrado"));

        // Cursos 
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No ha un curso con el id suministrado"));

        Message message = this.requestToEntity(request);

        message.setUserReceiver(userReceiver);
        message.setUserSender(userSender);
        message.setCourse(course);

        return this.entityToResponse(this.messageRepository.save(message));
    }

    // Optener solo uno
    @Override
    public MessageResp get(Long id) {

        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public MessageResp update(MessageReq request, Long id) {

        Message message = this.find(id);

        // Obtener el usuario
        UserEntity userReceiver = this.userRepository.findById(request.getUserReceiverId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario remitente con ese id suministrado"));

        UserEntity userSender = this.userRepository.findById(request.getUserSenderId())
                    .orElseThrow(() -> new BadRequestException("No ha un usuario remitente con ese id suministrado"));

        // Cursos 
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No ha un curso con el id suministrado"));

        message = this.requestToEntity(request);

        message.setMessageContent(request.getMessageContent());
        message.setSentDate(request.getSentDate());
        message.setUserReceiver(userReceiver);
        message.setUserSender(userSender);
        message.setCourse(course);

        return this.entityToResponse(this.messageRepository.save(message));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        this.messageRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<MessageResp> getAll(int page, int size) {

        if (page < 0) page = 1;

        PageRequest pagination = PageRequest.of(page - 1, size);

        return this.messageRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Metodos privados
    // Convertir
    private MessageResp entityToResponse(Message entity) {

        // Usuario remitente
        UserBasicResp userReceiver = new UserBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getUserReceiver() != null) {
            BeanUtils.copyProperties(entity.getUserReceiver(), userReceiver);
        }

        // Usuario 
        UserBasicResp userSender = new UserBasicResp();

        // Validar que el usuario es nulo 
        if (entity.getUserSender() != null) {
            BeanUtils.copyProperties(entity.getUserSender(), userSender);
        }

        // Curso
        CourseBasicResp course = new CourseBasicResp();

        if (entity.getCourse() != null) {
            BeanUtils.copyProperties(entity.getCourse(), course);
        }

        return MessageResp.builder()
                .id(entity.getId())
                .messageContent(entity.getMessageContent())
                .sentDate(entity.getSentDate())
                .userReceiver(userReceiver)
                .userSender(userSender)
                .build();
    }
    
    private Message requestToEntity(MessageReq requets) {

        return Message.builder()
                .messageContent(requets.getMessageContent())
                .sentDate(requets.getSentDate())
                .build();
    }

    // Buscar por id
    private Message find(Long id) {

        return this.messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay cursos con el id suministrado"));
    }
    
}
