package com.example.poo_advanced_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroAlunoRequest(

        @NotBlank(message = "informe o nome completo")
        @Size(max = 120)
        String nomeCompleto,

        @NotBlank(message = "informe o email")
        @Email(message = "email invalido")
        @Size(max = 120)
        String email,

        @NotBlank(message = "informe a senha")
        @Size(min = 8, max = 60, message = "a senha deve ter entre 8 e 60 caracteres")
        String senha,

        @NotBlank(message = "informe a matricula")
        @Size(max = 20)
        String matricula,

        @NotBlank(message = "informe o curso")
        @Size(max = 80)
        String curso,

        @NotNull(message = "informe a instituicao")
        Long instituicaoId
) {
}
