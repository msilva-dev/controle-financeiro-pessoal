package br.com.mateussilva.financeiro.domain.model.factory;

import br.com.mateussilva.financeiro.domain.model.transacao.DadosNovaTransacao;

import java.math.BigDecimal;

public class DadosNovaTransacaoFactory {

    private DadosNovaTransacaoFactory() {}

    public static DadosNovaTransacao umDepositoPadrao() {
        return new DadosNovaTransacao(
                "Origem Teste", new BigDecimal(100), "Origem Teste");
    }

    public static DadosNovaTransacao umDepositoComValor(BigDecimal valor) {
        return new DadosNovaTransacao(
                "Deposito com valor customizado", valor, "Origem Teste");
    }

    public static DadosNovaTransacao umSaquePadrao() {
        return new DadosNovaTransacao(
                "Saque Padrão", new BigDecimal(50), "Beneficiário Teste");
    }

    public static DadosNovaTransacao umSaqueComValor(BigDecimal valor) {
        return new DadosNovaTransacao(
                "Saque com valor customizado", valor, "Beneficiário Teste");
    }


}
