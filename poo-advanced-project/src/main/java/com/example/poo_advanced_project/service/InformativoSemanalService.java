package com.example.poo_advanced_project.service;

import com.example.poo_advanced_project.dto.InformativoResultado;
import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.model.Evento;
import com.example.poo_advanced_project.repository.AlunoRepository;
import com.example.poo_advanced_project.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InformativoSemanalService {

    private static final Logger log = LoggerFactory.getLogger(InformativoSemanalService.class);
    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy 'as' HH'h'mm");

    private final AlunoRepository alunoRepository;
    private final EventoRepository eventoRepository;
    private final EmailService emailService;

    // toda segunda-feira as 8h
    @Scheduled(cron = "0 0 8 * * MON")
    public void dispararAutomaticamente() {
        log.info("Disparo automatico do informativo semanal");
        enviar();
    }

    @Transactional(readOnly = true)
    public InformativoResultado enviar() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusDays(7);

        List<Aluno> inscritos = alunoRepository.findByAssinaturaAtivaTrue();
        List<String> falhas = new ArrayList<>();
        int enviados = 0;
        int semEventos = 0;

        for (Aluno aluno : inscritos) {
            List<Evento> eventos = eventoRepository
                    .findByInstituicaoIdAndDataHoraBetweenOrderByDataHoraAsc(
                            aluno.getInstituicao().getId(), inicio, fim);

            if (eventos.isEmpty()) {
                semEventos++;
                continue;
            }

            String retorno = emailService.enviarEmail(
                    aluno.getEmail(),
                    "Eventos da semana na " + aluno.getInstituicao().getSigla(),
                    montarMensagem(aluno, eventos));

            if (retorno.startsWith("Erro")) {
                falhas.add(aluno.getEmail() + ": " + retorno);
            } else {
                enviados++;
            }
        }

        log.info("Informativo semanal: {} inscritos, {} enviados, {} sem eventos, {} falhas",
                inscritos.size(), enviados, semEventos, falhas.size());

        return new InformativoResultado(
                LocalDateTime.now(), inicio, fim, inscritos.size(), enviados, semEventos, falhas);
    }

    private String montarMensagem(Aluno aluno, List<Evento> eventos) {
        StringBuilder texto = new StringBuilder();

        texto.append("Ola, ").append(aluno.getNomeCompleto()).append("!\n\n")
                .append("Estes sao os eventos da proxima semana na ")
                .append(aluno.getInstituicao().getNome())
                .append(" que valem horas complementares:\n\n");

        for (Evento evento : eventos) {
            texto.append("- ").append(evento.getNome()).append("\n")
                    .append("  Quando: ").append(evento.getDataHora().format(DATA_HORA)).append("\n")
                    .append("  Onde: ").append(evento.getLocal()).append("\n")
                    .append("  Categoria: ").append(evento.getCategoria()).append("\n")
                    .append("  ").append(evento.getDescricao()).append("\n\n");
        }

        texto.append("Para parar de receber este informativo, acesse seu cadastro no portal.\n");

        return texto.toString();
    }
}
