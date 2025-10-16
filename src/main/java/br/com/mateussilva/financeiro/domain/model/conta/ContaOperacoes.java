package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.transacao.DadosNovaTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.TipoTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.Transacao;

import java.math.BigDecimal;
import java.util.List;

public interface ContaOperacoes {
    void depositar(DadosNovaTransacao dados);
    void sacar(DadosNovaTransacao dados);
    List<Transacao> getExtrato();
}
