package com.example.poo_advanced_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** E-mail e senha do cadastro do aluno, usados para entrar e para ligar a assinatura. */
public record CredenciaisRequest(

        @NotBlank(message = "informe o email")
        @Email(message = "email invalido")
        String email,

        @NotBlank(message = "informe a senha")
        String senha
) {
}
