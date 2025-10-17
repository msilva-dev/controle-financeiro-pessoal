package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;

public class Carteira extends Conta {

    public Carteira(Usuario usuario) {
        super("Carteira", usuario);
    }

}
