package br.com.mateussilva.financeiro.domain.model.transacao;

import java.math.BigDecimal;

public record DadosNovaTransacao(String descricao,
                                 BigDecimal valor,
                                 String beneficiario) {
}
