package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.exception.CredenciaisInvalidasException;
import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Liga e desliga o recebimento do informativo semanal. */
@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AlunoRepository alunoRepository;
    private final AlunoService alunoService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AlunoResponse alterar(Long alunoId, boolean ativa) {
        Aluno aluno = alunoService.buscar(alunoId);
        aluno.setAssinaturaAtiva(ativa);
        return AlunoResponse.de(alunoRepository.save(aluno));
    }

    /** Cancelamento feito pelo proprio aluno, com as credenciais que ele cadastrou. */
    @Transactional
    public AlunoResponse cancelarComCredenciais(String email, String senha) {
        Aluno aluno = alunoRepository.findByEmailIgnoreCase(email.trim().toLowerCase())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(senha, aluno.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        aluno.setAssinaturaAtiva(false);
        return AlunoResponse.de(alunoRepository.save(aluno));
    }
}
