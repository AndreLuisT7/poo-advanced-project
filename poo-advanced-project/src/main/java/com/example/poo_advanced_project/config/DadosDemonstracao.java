package com.example.poo_advanced_project.config;

import com.example.poo_advanced_project.model.Evento;
import com.example.poo_advanced_project.model.Instituicao;
import com.example.poo_advanced_project.repository.EventoRepository;
import com.example.poo_advanced_project.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// carrega instituicoes e eventos ficticios so quando o banco esta vazio
@Component
@RequiredArgsConstructor
public class DadosDemonstracao implements CommandLineRunner {

    private final InstituicaoRepository instituicaoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public void run(String... args) {
        if (instituicaoRepository.count() > 0) {
            return;
        }

        Instituicao ucsal = salvarInstituicao("Universidade Catolica do Salvador", "UCSAL");
        salvarInstituicao("Instituto Federal da Bahia", "IFBA");

        LocalDateTime agora = LocalDateTime.now();

        salvarEvento(ucsal, "Semana de Tecnologia",
                "Palestras e oficinas sobre desenvolvimento de software e carreira em TI.",
                "Auditorio do Campus Pituacu", agora.plusDays(2), "Palestra", 4);

        salvarEvento(ucsal, "Oficina de Banco de Dados",
                "Oficina pratica de modelagem e consultas SQL.",
                "Laboratorio 3 - Campus Pituacu", agora.plusDays(4), "Oficina", 2);

        salvarEvento(ucsal, "Mostra de Projetos de Extensao",
                "Apresentacao dos projetos de extensao do semestre.",
                "Hall do Campus Pituacu", agora.plusDays(6), "Mostra", 3);

        salvarEvento(ucsal, "Congresso de Iniciacao Cientifica",
                "Apresentacao de trabalhos de iniciacao cientifica.",
                "Auditorio Central", agora.plusDays(20), "Congresso", 8);
    }

    private Instituicao salvarInstituicao(String nome, String sigla) {
        Instituicao instituicao = new Instituicao();
        instituicao.setNome(nome);
        instituicao.setSigla(sigla);
        return instituicaoRepository.save(instituicao);
    }

    private void salvarEvento(Instituicao instituicao, String nome, String descricao,
                              String local, LocalDateTime dataHora, String categoria,
                              int cargaHoraria) {
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setDescricao(descricao);
        evento.setLocal(local);
        evento.setDataHora(dataHora);
        evento.setCategoria(categoria);
        evento.setCargaHoraria(cargaHoraria);
        evento.setInstituicao(instituicao);
        eventoRepository.save(evento);
    }
}
