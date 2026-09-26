package com.example.poo_advanced_project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String remetente;

    @Autowired
    private JavaMailSender mailSender;
    public String enviarEmail(String destinatario, String assunto, String texto) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(texto);
            mailSender.send(simpleMailMessage);
            return "Email enviado com sucesso!";
        }catch (Exception e){
            return "Erro ao tentar enviar email" +e.getLocalizedMessage();
        }
    }


}
