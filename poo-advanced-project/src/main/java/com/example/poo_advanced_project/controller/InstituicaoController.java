package com.example.poo_advanced_project.controller;

import com.example.poo_advanced_project.dto.CadastroInstituicaoRequest;
import com.example.poo_advanced_project.dto.InstituicaoResponse;
import com.example.poo_advanced_project.service.InstituicaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/instituicoes")
@RequiredArgsConstructor
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    @PostMapping
    public ResponseEntity<InstituicaoResponse> cadastrar(
            @RequestBody @Valid CadastroInstituicaoRequest dados) {

        InstituicaoResponse instituicao = instituicaoService.cadastrar(dados);

        return ResponseEntity
                .created(URI.create("/api/instituicoes/" + instituicao.id()))
                .body(instituicao);
    }
}