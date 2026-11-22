package com.example.poo_advanced_project.dto;

import com.example.poo_advanced_project.model.Evento;

import java.time.LocalDateTime;

public record EventoResponse(
        Long id,
        String nome,
        String descricao,
        String local,
        LocalDateTime dataHora,
        String categoria,
        Integer cargaHoraria,
        Long instituicaoId
) {

    public static EventoResponse de(Evento evento) {
        return new EventoResponse(
                evento.getId(),
                evento.getNome(),
                evento.getDescricao(),
                evento.getLocal(),
                evento.getDataHora(),
                evento.getCategoria(),
                evento.getCargaHoraria(),
                evento.getInstituicao().getId()
        );
    }
}