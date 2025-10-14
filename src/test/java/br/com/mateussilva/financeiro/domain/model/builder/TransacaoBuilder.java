package br.com.mateussilva.financeiro.domain.model.builder;

import br.com.mateussilva.financeiro.domain.model.transacao.TipoTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.Transacao;

import java.math.BigDecimal;

public class TransacaoBuilder {

    private String descricao = "Lazer em familia no McDonald's";
    private BigDecimal valor = new BigDecimal(150);
    private TipoTransacao tipo = TipoTransacao.DESPESA;
    private String beneficiario = "McDonald's";

    public static  TransacaoBuilder umaTransacao() {
        return new TransacaoBuilder();
    }

    public TransacaoBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public TransacaoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TransacaoBuilder comTipo(TipoTransacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public TransacaoBuilder comBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
        return this;
    }

    public Transacao build() {
        return new Transacao(descricao, valor, tipo, beneficiario);
    }

}
