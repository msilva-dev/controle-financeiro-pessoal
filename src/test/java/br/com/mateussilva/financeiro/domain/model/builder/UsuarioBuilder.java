package br.com.mateussilva.financeiro.domain.model.builder;

import br.com.mateussilva.financeiro.domain.model.Usuario;

public class UsuarioBuilder {

    private String nome = "João Válido";
    private String email = "joao@valido.com";

    public static UsuarioBuilder umUsuario() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public Usuario build() {
        return new Usuario(this.nome, this.email);
    }
}
