package com.example.poo_advanced_project.service;

import com.vonage.client.VonageClient;
import com.vonage.client.messages.sms.SmsTextRequest;
import com.vonage.client.messages.MessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${vonage.api.key}")
    private String apiKey;

    @Value("${vonage.api.secret}")
    private String apiSecret;

    public void enviarSms(String destinatario, String texto) {
        try {
            VonageClient client = VonageClient.builder()
                    .apiKey(apiKey)
                    .apiSecret(apiSecret)
                    .build();

            MessageResponse response = client.getMessagesClient().sendMessage(
                    SmsTextRequest.builder()
                            .from("Vonage APIs")
                            .to(destinatario)
                            .text(texto)
                            .build()
            );

            System.out.println("SMS enviado. ID: " + response.getMessageUuid());
        } catch (Exception e) {
            System.err.println("Erro ao enviar SMS: " + e.getLocalizedMessage());
        }
    }
}