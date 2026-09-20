package com.example.poo_advanced_project.dto;

import com.example.poo_advanced_project.model.Aluno;

import java.time.LocalDateTime;

public record AlunoResponse(
        Long id,
        String nomeCompleto,
        String email,
        String matricula,
        String curso,
        String instituicao,
        boolean assinaturaAtiva,
        LocalDateTime dataCadastro
) {

    public static AlunoResponse de(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNomeCompleto(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getCurso(),
                aluno.getInstituicao().getNome(),
                aluno.isAssinaturaAtiva(),
                aluno.getDataCadastro()
        );
    }
}
