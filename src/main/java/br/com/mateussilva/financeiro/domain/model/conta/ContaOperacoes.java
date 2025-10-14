package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.transacao.Transacao;

import java.math.BigDecimal;
import java.util.List;

public interface ContaOperacoes {
    void depositar(BigDecimal valor);
    void sacar(BigDecimal valor);
    List<Transacao> getExtrato();
}
