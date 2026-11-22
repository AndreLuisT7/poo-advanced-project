package com.example.poo_advanced_project.service.notificacao;

/**
 * Canal de aviso ao aluno. O informativo semanal depende desta abstracao e nao
 * de um canal concreto, entao incluir SMS ou push no futuro e criar uma nova
 * implementacao, sem alterar quem envia.
 */
public interface Notificador {

    Resultado enviar(String destinatario, String assunto, String texto);

    record Resultado(boolean sucesso, String detalhe) {

        public static Resultado ok() {
            return new Resultado(true, null);
        }

        public static Resultado falha(String detalhe) {
            return new Resultado(false, detalhe);
        }
    }
}
