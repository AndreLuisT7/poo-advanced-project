package com.example.poo_advanced_project.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    // JavaMailSender e interface: o service nao sabe qual implementacao recebeu
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String remetente;

    public String enviarEmail(String destinatario, String assunto, String texto) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(destinatario);
            mensagem.setSubject(assunto);
            mensagem.setText(texto);

            mailSender.send(mensagem);

            log.info("E-mail enviado para {}", destinatario);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para {}: {}", destinatario, e.getLocalizedMessage());
            return "Erro ao tentar enviar email: " + e.getLocalizedMessage();
        }
    }
}
