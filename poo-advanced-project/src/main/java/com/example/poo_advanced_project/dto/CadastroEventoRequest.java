package com.example.poo_advanced_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CadastroEventoRequest(

        @NotBlank(message = "informe o nome do evento")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "informe a descricao")
        @Size(max = 2000)
        String descricao,

        @NotBlank(message = "informe o local")
        @Size(max = 150)
        String local,

        @NotNull(message = "informe a data e hora do evento")
        LocalDateTime dataHora,

        @NotBlank(message = "informe a categoria")
        @Size(max = 100)
        String categoria,

        @NotNull(message = "informe a carga horaria")
        @Min(value = 1, message = "a carga horaria deve ser maior que zero")
        Integer cargaHoraria,

        @NotNull(message = "informe a instituicao")
        Long instituicaoId
) {
}