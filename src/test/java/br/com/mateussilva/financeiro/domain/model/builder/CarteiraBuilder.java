package br.com.mateussilva.financeiro.domain.model.builder;

import br.com.mateussilva.financeiro.domain.model.conta.Carteira;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;

import static br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder.umUsuario;

public class CarteiraBuilder {

    private Usuario usuario = UsuarioBuilder.umUsuario().build();

    public static CarteiraBuilder umaCarteira() {
        return new CarteiraBuilder();
    }

    public CarteiraBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Carteira build() {
        return new Carteira(umUsuario().build());
    }

}
