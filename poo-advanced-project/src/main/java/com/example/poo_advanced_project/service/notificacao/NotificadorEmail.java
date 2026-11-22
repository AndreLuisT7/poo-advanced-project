package com.example.poo_advanced_project.service.notificacao;

import com.example.poo_advanced_project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// adapta o EmailService, que devolve String, ao contrato do Notificador
@Component
@Primary
@RequiredArgsConstructor
public class NotificadorEmail implements Notificador {

    private final EmailService emailService;

    @Override
    public Resultado enviar(String destinatario, String assunto, String texto) {
        String retorno = emailService.enviarEmail(destinatario, assunto, texto);
        return retorno.startsWith("Erro") ? Resultado.falha(retorno) : Resultado.ok();
    }
}
