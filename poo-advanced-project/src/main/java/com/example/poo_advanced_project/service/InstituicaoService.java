package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.CadastroInstituicaoRequest;
import com.example.poo_advanced_project.dto.InstituicaoResponse;
import com.example.poo_advanced_project.model.Instituicao;
import com.example.poo_advanced_project.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    @Transactional
    public InstituicaoResponse cadastrar(CadastroInstituicaoRequest dados) {

        Instituicao instituicao = new Instituicao();

        instituicao.setNome(dados.nome().trim());
        instituicao.setSigla(dados.sigla().trim().toUpperCase());

        return InstituicaoResponse.de(
                instituicaoRepository.save(instituicao)
        );
    }
}