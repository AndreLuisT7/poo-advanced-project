package com.example.poo_advanced_project.dto;

import com.example.poo_advanced_project.model.Instituicao;

public record InstituicaoResponse(
        Long id,
        String nome,
        String sigla
) {

    public static InstituicaoResponse de(Instituicao instituicao) {
        return new InstituicaoResponse(
                instituicao.getId(),
                instituicao.getNome(),
                instituicao.getSigla()
        );
    }
}