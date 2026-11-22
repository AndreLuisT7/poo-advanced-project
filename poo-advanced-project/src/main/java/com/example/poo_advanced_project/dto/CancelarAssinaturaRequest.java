package com.example.poo_advanced_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CancelarAssinaturaRequest(

        @NotBlank(message = "informe o email")
        @Email(message = "email invalido")
        String email,

        @NotBlank(message = "informe a senha")
        String senha
) {
}
