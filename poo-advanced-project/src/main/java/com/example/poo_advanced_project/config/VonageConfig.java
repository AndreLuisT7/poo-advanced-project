package com.example.poo_advanced_project.config;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * O cliente da Vonage passa a ser um bean unico. Antes ele era construido
 * dentro do metodo de envio, a cada SMS, o que impedia substituir a dependencia
 * em teste e recriava o objeto sem necessidade.
 */
@Configuration
public class VonageConfig {

    @Bean
    @Lazy
    public VonageClient vonageClient(@Value("${vonage.api.key:}") String apiKey,
                                     @Value("${vonage.api.secret:}") String apiSecret) {
        return VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }
}
