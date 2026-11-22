package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.dto.CadastroAlunoRequest;
import com.example.poo_advanced_project.service.AlunoService;
import com.example.poo_advanced_project.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;
    private final AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<AlunoResponse> cadastrar(@RequestBody @Valid CadastroAlunoRequest dados) {
        AlunoResponse aluno = alunoService.cadastrar(dados);
        return ResponseEntity.created(URI.create("/api/alunos/" + aluno.id())).body(aluno);
    }

    // dados pessoais: so a administracao consulta
    @GetMapping("/{id}")
    public AlunoResponse buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/assinatura")
    public AlunoResponse alterarAssinatura(@PathVariable Long id, @RequestParam boolean ativa) {
        return assinaturaService.alterar(id, ativa);
    }
}
