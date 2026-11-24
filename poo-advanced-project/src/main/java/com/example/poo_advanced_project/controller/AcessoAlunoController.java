package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.dto.CredenciaisRequest;
import com.example.poo_advanced_project.service.AcessoAlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Rotas publicas do portal do aluno: entrar e religar o informativo. */
@RestController
@RequiredArgsConstructor
public class AcessoAlunoController {

    private final AcessoAlunoService acessoAlunoService;

    @PostMapping("/api/alunos/login")
    public AlunoResponse entrar(@RequestBody @Valid CredenciaisRequest dados) {
        return acessoAlunoService.entrar(dados.email(), dados.senha());
    }

    @PostMapping("/api/assinaturas/ativar")
    public AlunoResponse ativar(@RequestBody @Valid CredenciaisRequest dados) {
        return acessoAlunoService.ativarAssinatura(dados.email(), dados.senha());
    }
}
