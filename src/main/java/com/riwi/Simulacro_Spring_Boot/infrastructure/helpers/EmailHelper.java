package com.riwi.Simulacro_Spring_Boot.infrastructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailHelper {
    
    // Inyectar el servicio de la libreia de email
    private final JavaMailSender mailSender;

    // Metodo para enviar el email
    public void sendMail(String destinity, String nameReceiver, String nameSender, LocalDateTime date) {

        MimeMessage message = mailSender.createMimeMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateMessage = date.format(formatter);
        String htmlContent = this.readHTMLTemplate(nameReceiver, nameSender, dateMessage);

        try {
            
            message.setFrom(new InternetAddress("danielajimenezhernandez6@gmail.com")); // Direccion de correo de la aplicacion
            message.setSubject("Confirmacion de envio de mensaje en Riwi"); // Asunto del correo 

            message.setRecipients(MimeMessage.RecipientType.TO, destinity); // El que lo va a enviar y a quien se lo voy a enviar
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE); // Le enviamos html y para especificar que es html le enviamos esta palabra "TEXT_HTML_VALUE"

            mailSender.send(message); // Pra enviar el emial
            System.out.println("Email enviado");

        } catch (Exception e) {
            System.out.println("ERROR no se pudo enviar el email" + e.getMessage());
        }
    }

    private String readHTMLTemplate(String nameReceiver, String nameSender, String date) {

        // Indicar en donde se encuentra el template
        final Path path = Paths.get("src\\main\\resources\\emails\\email_template.html");

        try (var lines = Files.lines(path)){

            var html = lines.collect(Collectors.joining()); // Unir las lineas que estan separadas

            return html.replace("name", nameSender).replace("userReceiver", nameReceiver).replace("date", date);
            

        } catch (IOException e) {
            
            System.out.println("No se pudo leer el HTML");
            throw new RuntimeException();
        }

   }
}
