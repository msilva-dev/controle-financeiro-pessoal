package br.com.mateussilva.financeiro.domain.model.transacao;

import br.com.mateussilva.financeiro.domain.model.conta.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transacao(UUID id,
                        String descricao,
                        BigDecimal valor,
                        TipoTransacao tipo,
                        LocalDateTime dataHora,
                        String destino) {

    Transacao(String descricao, BigDecimal valor, TipoTransacao tipo, String destino) {
        this(UUID.randomUUID(), descricao, valor, tipo, LocalDateTime.now(), destino);
    }

}
