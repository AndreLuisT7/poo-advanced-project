package com.example.poo_advanced_project.exception;

public class MatriculaJaCadastradaException extends RuntimeException {

    public MatriculaJaCadastradaException(String matricula, String sigla) {
        super("A matricula " + matricula + " ja esta cadastrada na " + sigla);
    }
}
