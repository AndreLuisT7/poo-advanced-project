package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.InformativoResultado;
import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.model.Evento;
import com.example.poo_advanced_project.repository.AlunoRepository;
import com.example.poo_advanced_project.repository.EventoRepository;
import com.example.poo_advanced_project.service.notificacao.Notificador;
import com.example.poo_advanced_project.service.notificacao.RedatorInformativo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Decide quem recebe o informativo e quando. Nao escreve a mensagem (RedatorInformativo)
 * nem sabe por qual canal ela sai (Notificador).
 */
@Service
@RequiredArgsConstructor
public class InformativoSemanalService {

    private static final Logger log = LoggerFactory.getLogger(InformativoSemanalService.class);

    private final AlunoRepository alunoRepository;
    private final EventoRepository eventoRepository;
    private final RedatorInformativo redator;
    private final Notificador notificador;

    // toda segunda-feira as 8h
    @Scheduled(cron = "${informativo.cron:0 0 8 * * MON}")
    public void dispararAutomaticamente() {
        log.info("Disparo automatico do informativo semanal");
        InformativoResultado resultado = enviar();
        log.info("Informativo automatico concluido: {} enviados de {} inscritos",
                resultado.emailsEnviados(), resultado.alunosInscritos());
    }

    public InformativoResultado enviar() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusDays(7);

        List<Destinatario> destinatarios = levantarDestinatarios(inicio, fim);

        List<String> falhas = new ArrayList<>();
        int enviados = 0;

        for (Destinatario destinatario : destinatarios) {
            if (destinatario.eventos().isEmpty()) {
                continue;
            }

            Aluno aluno = destinatario.aluno();
            Notificador.Resultado resultado = notificador.enviar(
                    aluno.getEmail(),
                    redator.assunto(aluno),
                    redator.corpo(aluno, destinatario.eventos()));

            if (resultado.sucesso()) {
                enviados++;
            } else {
                falhas.add(aluno.getEmail() + ": " + resultado.detalhe());
            }
        }

        int semEventos = (int) destinatarios.stream().filter(d -> d.eventos().isEmpty()).count();

        log.info("Informativo semanal: {} inscritos, {} enviados, {} sem eventos, {} falhas",
                destinatarios.size(), enviados, semEventos, falhas.size());

        return new InformativoResultado(
                LocalDateTime.now(), inicio, fim, destinatarios.size(), enviados, semEventos, falhas);
    }

    /**
     * Le tudo do banco antes de enviar, para que a conversa com o servidor de
     * e-mail aconteca fora de qualquer transacao aberta.
     *
     * Nao leva @Transactional de proposito: as consultas usam join fetch e ja
     * devolvem a instituicao carregada. Anotar aqui seria enganoso, porque o
     * metodo e chamado de dentro da propria classe e o proxy do Spring nao
     * entraria em acao. Foi exatamente esse detalhe que quebrava o envio
     * agendado com LazyInitializationException.
     */
    private List<Destinatario> levantarDestinatarios(LocalDateTime inicio, LocalDateTime fim) {
        return alunoRepository.buscarInscritosComInstituicao().stream()
                .map(aluno -> new Destinatario(aluno,
                        eventoRepository.buscarDaSemanaPorInstituicao(
                                aluno.getInstituicao().getId(), inicio, fim)))
                .toList();
    }

    private record Destinatario(Aluno aluno, List<Evento> eventos) {
    }
}
