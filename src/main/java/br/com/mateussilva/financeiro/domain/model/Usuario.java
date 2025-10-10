package br.com.mateussilva.financeiro.domain.model;

import br.com.mateussilva.financeiro.domain.util.ValidadorUtil;

import java.util.UUID;

public class Usuario {

    public static final String MSG_ERRO_NOME_INVALIDO = "ERRO: Nome não pode ser nulo ou vazio.";
    public static final String MSG_ERRO_EMAIL_INVALIDO = "ERRO: Email não pode ser nulo ou vazio.";
    public static final String MSG_ERRO_EMAIL_FORMATO = "ERRO: Formato de e-mail inválido.";

    private final UUID id;
    private String nome;
    private String email;

    //private final List<Conta> contas;


    public Usuario(String nome, String email) {
        validarNome(nome);
        validarEmail(email);

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
    }


    public void alterarNome(String novoNome) {
        validarNome(novoNome);
        this.nome = novoNome;
    }
    public void alterarEmail(String novoEmail) {
        validarEmail(novoEmail);
        this.email = novoEmail;
    }


    public UUID getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }


    private void validarNome(String nome) {
        if (nome == null || nome.trim().isBlank()) {
            throw new IllegalArgumentException(MSG_ERRO_NOME_INVALIDO);
        }
    }
    private void validarEmail(String email) {
        if (email == null || email.trim().isBlank()) {
            throw new IllegalArgumentException(MSG_ERRO_EMAIL_INVALIDO);
        }
        if (!ValidadorUtil.isEmailValido(email)) {
            throw new IllegalArgumentException(MSG_ERRO_EMAIL_FORMATO);
        }
    }

}
