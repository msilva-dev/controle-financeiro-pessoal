package br.com.mateussilva.financeiro.domain.model.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transacao(UUID id,
                        String descricao,
                        BigDecimal valor,
                        TipoTransacao tipo,
                        LocalDateTime dataHora,
                        String beneficiario) {

    public Transacao {
        if (descricao == null || descricao.trim().isBlank()) {
            throw new IllegalArgumentException(MSG_DESCRICAO_INVALIDA);
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MSG_VALOR_INVALIDO);
        }
        if (tipo == null) {
            throw new IllegalArgumentException(MSG_TIPO_TRANSACAO_INVALIDA);
        }
        if (beneficiario == null || beneficiario.trim().isBlank()) {
            throw new IllegalArgumentException(MSG_BENEFICIARIO_INVALIDO);
        }
    }

    public Transacao(String descricao, BigDecimal valor, TipoTransacao tipo, String beneficiario) {
        this(UUID.randomUUID(), descricao, valor, tipo, LocalDateTime.now(), beneficiario);
    }

    public static final String MSG_DESCRICAO_INVALIDA = "ERRO: Uma descrição deve ser informada.";
    public static final String MSG_TIPO_TRANSACAO_INVALIDA = "ERRO: Um tipo de transação deve ser informada.";
    public static final String MSG_BENEFICIARIO_INVALIDO = "ERRO: O beneficiário da transação deve ser informado.";
    public static final String MSG_VALOR_INVALIDO = "ERRO: O valor deve ser maior que zero.";
}
