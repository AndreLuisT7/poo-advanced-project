package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.CadastroEventoRequest;
import com.example.poo_advanced_project.dto.EventoResponse;
import com.example.poo_advanced_project.exception.RecursoNaoEncontradoException;
import com.example.poo_advanced_project.model.Evento;
import com.example.poo_advanced_project.model.Instituicao;
import com.example.poo_advanced_project.repository.EventoRepository;
import com.example.poo_advanced_project.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InstituicaoRepository instituicaoRepository;

    @Transactional
    public EventoResponse cadastrar(CadastroEventoRequest dados) {

        Instituicao instituicao = instituicaoRepository.findById(dados.instituicaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Instituicao " + dados.instituicaoId() + " nao encontrada"));

        Evento evento = new Evento();

        evento.setNome(dados.nome().trim());
        evento.setDescricao(dados.descricao().trim());
        evento.setLocal(dados.local().trim());
        evento.setDataHora(dados.dataHora());
        evento.setCategoria(dados.categoria().trim());
        evento.setInstituicao(instituicao);

        return EventoResponse.de(
                eventoRepository.save(evento)
        );
    }
}