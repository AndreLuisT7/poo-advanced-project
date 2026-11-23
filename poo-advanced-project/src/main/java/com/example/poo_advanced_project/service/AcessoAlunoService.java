package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.exception.CredenciaisInvalidasException;
import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Entrada do aluno no portal. Nao ha sessao no servidor: cada chamada confere o
 * e-mail e a senha cadastrados (BCrypt), igual ao cancelamento da assinatura.
 */
@Service
@RequiredArgsConstructor
public class AcessoAlunoService {

    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AlunoResponse entrar(String email, String senha) {
        return AlunoResponse.de(autenticar(email, senha));
    }

    /** Religa o informativo (o cancelamento ja existe em AssinaturaService). */
    @Transactional
    public AlunoResponse ativarAssinatura(String email, String senha) {
        Aluno aluno = autenticar(email, senha);
        aluno.setAssinaturaAtiva(true);
        return AlunoResponse.de(alunoRepository.save(aluno));
    }

    private Aluno autenticar(String email, String senha) {
        Aluno aluno = alunoRepository.findByEmailIgnoreCase(email.trim().toLowerCase())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(senha, aluno.getSenha())) {
            throw new CredenciaisInvalidasException();
        }
        return aluno;
    }
}
