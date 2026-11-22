package com.example.poo_advanced_project.service;

import com.vonage.client.VonageClient;
import com.vonage.client.messages.MessageResponse;
import com.vonage.client.messages.sms.SmsTextRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    private final ObjectProvider<VonageClient> vonageClient;

    @Value("${vonage.remetente:Vonage APIs}")
    private String remetente;

    public String enviarSms(String destinatario, String texto) {
        try {
            MessageResponse resposta = vonageClient.getObject().getMessagesClient().sendMessage(
                    SmsTextRequest.builder()
                            .from(remetente)
                            .to(destinatario)
                            .text(texto)
                            .build());

            log.info("SMS enviado para {}. ID: {}", destinatario, resposta.getMessageUuid());
            return "SMS enviado com sucesso!";
        } catch (Exception e) {
            log.error("Erro ao enviar SMS para {}: {}", destinatario, e.getLocalizedMessage());
            return "Erro ao tentar enviar SMS: " + e.getLocalizedMessage();
        }
    }
}
