package br.com.mateussilva.financeiro.domain.model.builder;

import br.com.mateussilva.financeiro.domain.model.conta.ContaBancaria;
import br.com.mateussilva.financeiro.domain.model.conta.TipoConta;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;

import java.math.BigDecimal;

public class ContaBancariaBuilder {

    private String nome = "Conta Corrente Itaú";
    private String agencia = "1234";
    private String conta = "123456";
    private BigDecimal saldo = BigDecimal.ZERO;
    private TipoConta tipo = TipoConta.CORRENTE;
    private Usuario usuario = new Usuario("Mateus", "email@valido.com");

    public static ContaBancariaBuilder umaContaBancaria() {
        return new ContaBancariaBuilder();
    }

    public ContaBancariaBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaBancariaBuilder comAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public ContaBancariaBuilder comConta(String conta) {
        this.conta = conta;
        return this;
    }

    public ContaBancariaBuilder comTipo(TipoConta tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContaBancariaBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public ContaBancariaBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public ContaBancaria build() {
        return new ContaBancaria(nome, usuario, this.saldo, agencia, conta, tipo);
    }
}
