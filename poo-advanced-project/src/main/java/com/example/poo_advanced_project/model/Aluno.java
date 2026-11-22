package com.example.poo_advanced_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "aluno", uniqueConstraints = {
        @UniqueConstraint(name = "uk_aluno_matricula_instituicao",
                columnNames = {"matricula", "instituicao_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, length = 20)
    private String matricula;

    @Column(nullable = false, length = 80)
    private String curso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    // controla se o aluno recebe o email semanal de eventos
    @Column(nullable = false)
    private boolean assinaturaAtiva = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    private void aoCadastrar() {
        this.dataCadastro = LocalDateTime.now();
    }
}
