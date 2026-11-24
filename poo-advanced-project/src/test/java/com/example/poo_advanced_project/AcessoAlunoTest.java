package com.example.poo_advanced_project;

import com.example.poo_advanced_project.dto.CadastroAlunoRequest;
import com.example.poo_advanced_project.exception.CredenciaisInvalidasException;
import com.example.poo_advanced_project.service.AcessoAlunoService;
import com.example.poo_advanced_project.service.AlunoService;
import com.example.poo_advanced_project.service.AssinaturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AcessoAlunoTest {

    @Autowired
    private AcessoAlunoService acessoAlunoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssinaturaService assinaturaService;

    private CadastroAlunoRequest pedido(String email, String matricula) {
        return new CadastroAlunoRequest("Aluno de Teste", email, "senha12345",
                matricula, "Analise e Desenvolvimento", 1L);
    }

    @Test
    void alunoEntraComEmailESenhaCorretos() {
        alunoService.cadastrar(pedido("login@ucsal.edu.br", "810001"));

        var resposta = acessoAlunoService.entrar("LOGIN@ucsal.edu.br", "senha12345");

        assertThat(resposta.assinaturaAtiva()).isTrue();
    }

    @Test
    void recusaSenhaErradaEEmailDesconhecido() {
        alunoService.cadastrar(pedido("errada@ucsal.edu.br", "810002"));

        assertThatThrownBy(() -> acessoAlunoService.entrar("errada@ucsal.edu.br", "outra-senha"))
                .isInstanceOf(CredenciaisInvalidasException.class);
        assertThatThrownBy(() -> acessoAlunoService.entrar("naoexiste@ucsal.edu.br", "senha12345"))
                .isInstanceOf(CredenciaisInvalidasException.class);
    }

    @Test
    void alunoCancelaEReativaOInformativo() {
        var aluno = alunoService.cadastrar(pedido("religa@ucsal.edu.br", "810003"));

        var cancelada = assinaturaService.cancelarComCredenciais("religa@ucsal.edu.br", "senha12345");
        assertThat(cancelada.assinaturaAtiva()).isFalse();

        var religada = acessoAlunoService.ativarAssinatura("religa@ucsal.edu.br", "senha12345");
        assertThat(religada.assinaturaAtiva()).isTrue();
        assertThat(religada.id()).isEqualTo(aluno.id());
    }

    @Test
    void reativarExigeSenhaCorreta() {
        alunoService.cadastrar(pedido("semsenha@ucsal.edu.br", "810004"));

        assertThatThrownBy(() -> acessoAlunoService.ativarAssinatura("semsenha@ucsal.edu.br", "errada"))
                .isInstanceOf(CredenciaisInvalidasException.class);
    }
}
