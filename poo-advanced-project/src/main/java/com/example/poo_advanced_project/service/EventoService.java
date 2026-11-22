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

import java.time.LocalDateTime;
import java.util.List;

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
        evento.setCargaHoraria(dados.cargaHoraria());
        evento.setInstituicao(instituicao);

        return EventoResponse.de(
                eventoRepository.save(evento)
        );
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> listar() {
        return eventoRepository.buscarTodosComInstituicao().stream()
                .map(EventoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> listarProximos() {
        return eventoRepository.buscarProximos(LocalDateTime.now()).stream()
                .map(EventoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventoResponse buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento " + id + " nao encontrado"));
        return EventoResponse.de(evento);
    }
}
