package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.dto.CadastroAlunoRequest;
import com.example.poo_advanced_project.exception.EmailJaCadastradoException;
import com.example.poo_advanced_project.exception.RecursoNaoEncontradoException;
import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.model.Instituicao;
import com.example.poo_advanced_project.repository.AlunoRepository;
import com.example.poo_advanced_project.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AlunoResponse cadastrar(CadastroAlunoRequest dados) {
        String email = dados.email().trim().toLowerCase();

        if (alunoRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailJaCadastradoException(email);
        }

        Instituicao instituicao = instituicaoRepository.findById(dados.instituicaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Instituicao " + dados.instituicaoId() + " nao encontrada"));

        Aluno aluno = new Aluno();
        aluno.setNomeCompleto(dados.nomeCompleto().trim());
        aluno.setEmail(email);
        aluno.setSenha(passwordEncoder.encode(dados.senha()));
        aluno.setMatricula(dados.matricula().trim());
        aluno.setCurso(dados.curso().trim());
        aluno.setInstituicao(instituicao);
        aluno.setAssinaturaAtiva(true);

        return AlunoResponse.de(alunoRepository.save(aluno));
    }

    @Transactional(readOnly = true)
    public AlunoResponse buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno " + id + " nao encontrado"));
        return AlunoResponse.de(aluno);
    }

    @Transactional
    public AlunoResponse alterarAssinatura(Long id, boolean ativa) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno " + id + " nao encontrado"));
        aluno.setAssinaturaAtiva(ativa);
        return AlunoResponse.de(alunoRepository.save(aluno));
    }
}
