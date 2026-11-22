package com.example.poo_advanced_project.service.notificacao;

import com.example.poo_advanced_project.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Segunda implementacao de Notificador. Existe para mostrar que trocar o canal
 * do informativo nao exige alterar o InformativoSemanalService: basta mudar qual
 * implementacao e a primaria.
 */
@Component
@RequiredArgsConstructor
public class NotificadorSms implements Notificador {

    private final SmsService smsService;

    @Override
    public Resultado enviar(String destinatario, String assunto, String texto) {
        String retorno = smsService.enviarSms(destinatario, assunto + "\n" + texto);
        return retorno.startsWith("Erro") ? Resultado.falha(retorno) : Resultado.ok();
    }
}
