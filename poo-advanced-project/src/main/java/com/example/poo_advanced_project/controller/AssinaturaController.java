package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.AlunoResponse;
import com.example.poo_advanced_project.dto.CancelarAssinaturaRequest;
import com.example.poo_advanced_project.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    // o proprio aluno cancela, provando quem e com o e-mail e a senha do cadastro
    @PostMapping("/cancelar")
    public AlunoResponse cancelar(@RequestBody @Valid CancelarAssinaturaRequest dados) {
        return assinaturaService.cancelarComCredenciais(dados.email(), dados.senha());
    }
}
