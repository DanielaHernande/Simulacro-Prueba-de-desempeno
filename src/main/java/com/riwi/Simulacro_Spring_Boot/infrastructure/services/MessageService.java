package com.riwi.Simulacro_Spring_Boot.infrastructure.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
import com.riwi.Simulacro_Spring_Boot.infrastructure.helpers.EmailHelper;
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

    // Email
    @Autowired
    private final EmailHelper emailHelper;

    // Crear
    @Override
    public MessageResp create(MessageReq request) {

         // Obtener el usuario receptor por ID, lanzar excepción si no se encuentra
        UserEntity userReceiver = this.userRepository.findById(request.getUserReceiverId())
                    .orElseThrow(() -> new BadRequestException("No hay un usuario remitente con ese id suministrado"));

        // Obtener el usuario remitente por ID, lanzar excepción si no se encuentra
        UserEntity userSender = this.userRepository.findById(request.getUserSenderId())
                    .orElseThrow(() -> new BadRequestException("No hay un usuario remitente con ese id suministrado"));

        // Obtener el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No hay un curso con el id suministrado"));


        // Convertir la solicitud a entidad Message
        Message message = this.requestToEntity(request);

        // Asignar el usuario receptor, remitente y curso al mensaje
        message.setUserReceiver(userReceiver);
        message.setUserSender(userSender);
        message.setCourse(course);

        // Obtener la fecha de envío del mensaje
        java.sql.Date sentDate = message.getSentDate();

        // Si la fecha de envío no es nula
        if (sentDate != null) {

            // Convertir java.sql.Date a LocalDate
            LocalDate localDate = sentDate.toLocalDate();
            
            // Convertir LocalDate a LocalDateTime
            LocalDateTime sentDateTime = localDate.atStartOfDay();

             // Si el correo del receptor no es nulo, enviar un correo electrónico
            if (Objects.nonNull(userReceiver.getEmail())) {

                this.emailHelper.sendMail(userReceiver.getEmail(), userReceiver.getFullName(),
                userSender.getFullName(), sentDateTime);
            }
        }

        // Guardar el mensaje en el repositorio y devolver la respuesta
        return this.entityToResponse(this.messageRepository.save(message));
    }

    // Optener solo uno
    @Override
    public MessageResp get(Long id) {

        // Buscar el mensaje y convertirlo a respuesta
        return this.entityToResponse(this.find(id));
    }

    // Actualizar
    @Override
    public MessageResp update(MessageReq request, Long id) {

        // Buscar el mensaje existente por ID
        Message message = this.find(id);

        // Obtener el usuario receptor por ID, lanzar excepción si no se encuentra
        UserEntity userReceiver = this.userRepository.findById(request.getUserReceiverId())
                    .orElseThrow(() -> new BadRequestException("No hay un usuario remitente con ese id suministrado"));

        // Obtener el usuario remitente por ID, lanzar excepción si no se encuentra
        UserEntity userSender = this.userRepository.findById(request.getUserSenderId())
                    .orElseThrow(() -> new BadRequestException("No hay un usuario remitente con ese id suministrado"));

        // Obtener el curso por ID, lanzar excepción si no se encuentra
        Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("No hay un curso con el id suministrado"));

        // Actualizar el contenido del mensaje, la fecha de envío, el usuario receptor, el remitente y el curso   
        message.setMessageContent(request.getMessageContent());
        message.setSentDate(request.getSentDate());
        message.setUserReceiver(userReceiver);
        message.setUserSender(userSender);
        message.setCourse(course);

        // Guardar el mensaje actualizado en el repositorio y devolver la respuesta
        return this.entityToResponse(this.messageRepository.save(message));
    }

    // Eliminar
    @Override
    public void delete(Long id) {

        // Eliminar el mensaje del repositorio
        this.messageRepository.delete(this.find(id));
    }

    // Obtener todo
    @Override
    public Page<MessageResp> getAll(int page, int size) {

        // Ajustar el número de página si es necesario
        if (page < 0) page = 1;

        // Crear objeto de paginación
        PageRequest pagination = PageRequest.of(page - 1, size);

        // Obtener todos los mensajes paginados y convertirlos a respuestas
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

        // Crear objeto básico de respuesta para el curso
        CourseBasicResp course = new CourseBasicResp();

        // Copiar propiedades del curso si no es nulo
        if (entity.getCourse() != null) {
            BeanUtils.copyProperties(entity.getCourse(), course);
        }

        // Construir y devolver la respuesta del mensaje
        return MessageResp.builder()
                .id(entity.getId())
                .messageContent(entity.getMessageContent())
                .sentDate(entity.getSentDate())
                .userReceiver(userReceiver)
                .userSender(userSender)
                .course(course)
                .build();
    }
    
    // Convertir solicitud MessageReq a entidad Message
    private Message requestToEntity(MessageReq requets) {

        return Message.builder()
                .messageContent(requets.getMessageContent())
                .sentDate(requets.getSentDate())
                .build();
    }

    // Buscar mensaje por ID y lanzar excepción si no se encuentra
    private Message find(Long id) {

        return this.messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No hay mensajes con el id suministrado"));
    }
    
}
