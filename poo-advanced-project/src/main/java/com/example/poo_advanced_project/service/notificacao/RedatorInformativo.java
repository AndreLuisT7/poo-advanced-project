package com.example.poo_advanced_project.service.notificacao;

import com.example.poo_advanced_project.model.Aluno;
import com.example.poo_advanced_project.model.Evento;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Escreve o texto do informativo. Fica separado do servico de envio porque
 * mudar a redacao da mensagem nao e o mesmo motivo que mudar quem recebe.
 */
@Component
public class RedatorInformativo {

    private static final DateTimeFormatter DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'as' HH'h'mm");

    public String assunto(Aluno aluno) {
        return "Eventos da semana na " + aluno.getInstituicao().getSigla();
    }

    public String corpo(Aluno aluno, List<Evento> eventos) {
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
                    .append("  Horas complementares: ").append(evento.getCargaHoraria()).append("h\n")
                    .append("  ").append(evento.getDescricao()).append("\n\n");
        }

        texto.append("Total disponivel na semana: ")
                .append(eventos.stream().mapToInt(Evento::getCargaHoraria).sum())
                .append(" horas.\n\n")
                .append("Para parar de receber este informativo, envie uma requisicao\n")
                .append("POST /api/assinaturas/cancelar com o seu e-mail e a sua senha.\n");

        return texto.toString();
    }
}
