package com.example.poo_advanced_project;

import com.example.poo_advanced_project.dto.CadastroAlunoRequest;
import com.example.poo_advanced_project.service.AlunoService;
import com.example.poo_advanced_project.service.AssinaturaService;
import com.example.poo_advanced_project.service.InformativoSemanalService;
import com.example.poo_advanced_project.service.notificacao.Notificador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Troca o Notificador real por um falso que so anota o que receberia.
 * So e possivel porque o servico depende da interface, e nao do EmailService.
 */
@SpringBootTest
class InformativoSemanalTest {

    static class NotificadorFalso implements Notificador {
        final List<String> enviados = new ArrayList<>();
        final List<String> corpos = new ArrayList<>();

        @Override
        public Resultado enviar(String destinatario, String assunto, String texto) {
            enviados.add(destinatario);
            corpos.add(texto);
            return Resultado.ok();
        }
    }

    @TestConfiguration
    static class Config {
        // substitui o bean real pelo falso, mantendo o nome para nao criar dois primarios
        @Bean("notificadorEmail")
        @Primary
        NotificadorFalso notificadorFalso() {
            return new NotificadorFalso();
        }
    }

    @Autowired
    private InformativoSemanalService informativo;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private NotificadorFalso notificador;

    @BeforeEach
    void limpar() {
        notificador.enviados.clear();
        notificador.corpos.clear();
    }

    @Test
    void enviaParaInscritoComEventosNaSemanaEIncluiCargaHoraria() {
        alunoService.cadastrar(new CadastroAlunoRequest("Maria Souza", "maria.inf@ucsal.edu.br",
                "senha12345", "800001", "ADS", 1L));

        var resultado = informativo.enviar();

        assertThat(resultado.emailsEnviados()).isPositive();
        assertThat(resultado.falhas()).isEmpty();
        assertThat(notificador.enviados).contains("maria.inf@ucsal.edu.br");
        assertThat(notificador.corpos.get(0)).contains("Horas complementares:");
    }

    @Test
    void naoEnviaParaQuemCancelouAAssinatura() {
        var aluno = alunoService.cadastrar(new CadastroAlunoRequest("Joao Lima", "joao.inf@ucsal.edu.br",
                "senha12345", "800002", "ADS", 1L));

        assinaturaService.alterar(aluno.id(), false);
        notificador.enviados.clear();

        informativo.enviar();

        assertThat(notificador.enviados).doesNotContain("joao.inf@ucsal.edu.br");
    }

    @Test
    void cancelamentoPeloProprioAlunoExigeSenhaCorreta() {
        alunoService.cadastrar(new CadastroAlunoRequest("Ana Dias", "ana.inf@ucsal.edu.br",
                "senha12345", "800003", "ADS", 1L));

        var resposta = assinaturaService.cancelarComCredenciais("ana.inf@ucsal.edu.br", "senha12345");

        assertThat(resposta.assinaturaAtiva()).isFalse();
    }
}
