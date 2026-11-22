package com.example.poo_advanced_project;

import com.example.poo_advanced_project.dto.CadastroAlunoRequest;
import com.example.poo_advanced_project.exception.EmailJaCadastradoException;
import com.example.poo_advanced_project.exception.MatriculaJaCadastradaException;
import com.example.poo_advanced_project.exception.RecursoNaoEncontradoException;
import com.example.poo_advanced_project.repository.AlunoRepository;
import com.example.poo_advanced_project.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CadastroAlunoTest {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private CadastroAlunoRequest pedido(String email, String matricula) {
        return new CadastroAlunoRequest("Aluno de Teste", email, "senha12345",
                matricula, "Analise e Desenvolvimento", 1L);
    }

    @Test
    void cadastraAlunoComAssinaturaAtivaESenhaCriptografada() {
        var resposta = alunoService.cadastrar(pedido("novo@ucsal.edu.br", "900001"));

        assertThat(resposta.assinaturaAtiva()).isTrue();
        assertThat(resposta.instituicao()).isNotBlank();

        var salvo = alunoRepository.findByEmailIgnoreCase("novo@ucsal.edu.br").orElseThrow();
        assertThat(salvo.getSenha()).isNotEqualTo("senha12345");
        assertThat(passwordEncoder.matches("senha12345", salvo.getSenha())).isTrue();
    }

    @Test
    void recusaEmailRepetido() {
        alunoService.cadastrar(pedido("repetido@ucsal.edu.br", "900002"));

        assertThatThrownBy(() -> alunoService.cadastrar(pedido("REPETIDO@ucsal.edu.br", "900003")))
                .isInstanceOf(EmailJaCadastradoException.class);
    }

    @Test
    void recusaMatriculaRepetidaNaMesmaInstituicao() {
        alunoService.cadastrar(pedido("primeiro@ucsal.edu.br", "900004"));

        assertThatThrownBy(() -> alunoService.cadastrar(pedido("segundo@ucsal.edu.br", "900004")))
                .isInstanceOf(MatriculaJaCadastradaException.class);
    }

    @Test
    void recusaInstituicaoInexistente() {
        var pedido = new CadastroAlunoRequest("Aluno", "sem.inst@ucsal.edu.br", "senha12345",
                "900005", "ADS", 9999L);

        assertThatThrownBy(() -> alunoService.cadastrar(pedido))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
