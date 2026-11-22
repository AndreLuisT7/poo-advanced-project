package com.example.poo_advanced_project.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InformativoResultado(
        LocalDateTime geradoEm,
        LocalDateTime inicioDaSemana,
        LocalDateTime fimDaSemana,
        int alunosInscritos,
        int emailsEnviados,
        int alunosSemEventos,
        List<String> falhas
) {
}
