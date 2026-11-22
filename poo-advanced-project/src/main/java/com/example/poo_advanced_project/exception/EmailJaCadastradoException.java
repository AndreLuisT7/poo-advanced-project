package com.example.poo_advanced_project.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Ja existe um aluno cadastrado com o email " + email);
    }
}
