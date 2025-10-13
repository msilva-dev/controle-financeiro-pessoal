package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;

import java.math.BigDecimal;

public class ContaBancaria extends Conta{

    public static final String MSG_ERRO_AGENCIA_INVALIDO = "ERRO: Agência não pode ser nulo ou vazio.";
    public static final String MSG_ERRO_CONTA_INVALIDO = "ERRO: Conta não pode ser nulo ou vazio.";
    public static final String MSG_ERRO_TIPO_CONTA_INVALIDO = "ERRO: Tipo não pode ser nulo, vazio ou diferente de CORRENTE ou POUPANCA.";

    private final String agencia;
    private final String conta;
    private final TipoConta tipo;

    public ContaBancaria(String nome, Usuario usuario, String agencia, String conta, TipoConta tipo) {
        super(nome, usuario);

        validarCriacaoConta(agencia, conta, tipo);

        this.agencia = agencia;
        this.conta = conta;
        this.tipo = tipo;
    }
    public ContaBancaria(String nome, Usuario usuario, BigDecimal saldoInicial, String agencia, String conta, TipoConta tipo) {
        this(nome, usuario, agencia, conta, tipo);
        this.setSaldo(saldoInicial);
    }

    public String getAgencia() {
        return agencia;
    }
    public String getConta() {
        return conta;
    }
    public TipoConta getTipo() {
        return tipo;
    }

    private void validarCriacaoConta(String agencia, String conta, TipoConta tipo) {
        if (agencia == null || agencia.trim().isBlank() || !agencia.matches("\\d+")) {
            throw new IllegalArgumentException(MSG_ERRO_AGENCIA_INVALIDO);
        }
        if (conta == null || conta.trim().isBlank() || !conta.matches("\\d+")) {
            throw new IllegalArgumentException(MSG_ERRO_CONTA_INVALIDO);
        }
        if (tipo != TipoConta.CORRENTE && tipo != TipoConta.POUPANCA) {
                throw new IllegalArgumentException(MSG_ERRO_TIPO_CONTA_INVALIDO);
        }
    }
}
