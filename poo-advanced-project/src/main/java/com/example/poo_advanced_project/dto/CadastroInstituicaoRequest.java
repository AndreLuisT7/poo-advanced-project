package com.example.poo_advanced_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroInstituicaoRequest(

        @NotBlank(message = "informe o nome da instituicao")
        @Size(max = 120)
        String nome,

        @NotBlank(message = "informe a sigla")
        @Size(max = 10)
        String sigla
) {
}