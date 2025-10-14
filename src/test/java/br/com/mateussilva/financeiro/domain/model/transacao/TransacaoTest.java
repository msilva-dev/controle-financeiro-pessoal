package br.com.mateussilva.financeiro.domain.model.transacao;

import br.com.mateussilva.financeiro.domain.model.builder.TransacaoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static br.com.mateussilva.financeiro.domain.model.transacao.Transacao.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TransacaoTest {

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        transacao = TransacaoBuilder.umaTransacao()
                .comDescricao("Lazer em familia no McDonald's")
                .comValor(new BigDecimal(150))
                .comBeneficiario("McDonald's")
                .build();
    }

    static Stream<Arguments> dadosValidosParaCriacao() {
        return Stream.of(
                Arguments.of("Salário", new BigDecimal("5000"), TipoTransacao.RECEITA, "Empresa X"),
                Arguments.of("Almoço", new BigDecimal("45.50"), TipoTransacao.DESPESA, "Restaurante Y")
        );
    }

    static Stream<Arguments> descricaoInvalida() {
        return Stream.of(
                Arguments.of(null, MSG_DESCRICAO_INVALIDA),
                Arguments.of("", MSG_DESCRICAO_INVALIDA),
                Arguments.of("   ", MSG_DESCRICAO_INVALIDA)
        );
    }
    static Stream<Arguments> beneficiarioInvalido() {
        return Stream.of(
                Arguments.of(null, MSG_BENEFICIARIO_INVALIDO),
                Arguments.of("", MSG_BENEFICIARIO_INVALIDO),
                Arguments.of("   ", MSG_BENEFICIARIO_INVALIDO)
        );
    }
    static Stream<Arguments> valoresDeTransacaoInvalidos() {
        return Stream.of(
                Arguments.of(BigDecimal.ZERO, MSG_VALOR_INVALIDO),
                Arguments.of(BigDecimal.valueOf(-0.01), MSG_VALOR_INVALIDO),
                Arguments.of(BigDecimal.valueOf(-10), MSG_VALOR_INVALIDO),
                Arguments.of(BigDecimal.valueOf(-100), MSG_VALOR_INVALIDO)
        );
    }

    private void deveLancarErroAoCriarTransacao(String descricao, BigDecimal valor, TipoTransacao tipo, String beneficiario, String msgEsperada) {
        assertThatThrownBy(() -> TransacaoBuilder.umaTransacao()
                .comDescricao(descricao)
                .comValor(valor)
                .comTipo(tipo)
                .comBeneficiario(beneficiario)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }


    @ParameterizedTest
    @MethodSource("dadosValidosParaCriacao")
    @DisplayName("Dado dados válidos, quando criar transação, então deve inicializar todos os campos corretamente")
    void deveCriarTransacaoComSucesso(String desc, BigDecimal val, TipoTransacao tipo, String benef) {
        Transacao transacao = TransacaoBuilder.umaTransacao()
                .comDescricao(desc).comValor(val).comTipo(tipo).comBeneficiario(benef).build();

        assertThat(transacao.id()).isNotNull();
        assertThat(transacao.dataHora()).isNotNull();
        assertThat(transacao.descricao()).isEqualTo(desc);
        assertThat(transacao.valor()).isEqualByComparingTo(val);
        assertThat(transacao.tipo()).isEqualTo(tipo);
        assertThat(transacao.beneficiario()).isEqualTo(benef);
    }

    @ParameterizedTest
    @MethodSource("descricaoInvalida")
    @DisplayName("Dado uma descrição inválida, quando criar transação, então deve lançar uma exceção")
    void naoDeveCriarTransacaoComDescricaoInvalida(String descricaoInvalida, String msgEsperada) {
        deveLancarErroAoCriarTransacao(descricaoInvalida, new BigDecimal(150), TipoTransacao.DESPESA, "McDonald's", msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("valoresDeTransacaoInvalidos")
    @DisplayName("Dado um valor inválido, quando criar transação, então deve lançar uma exceção")
    void naoDeveCriarTransacaoComValorInvalido(BigDecimal valorInvalido, String msgEsperada) {
        deveLancarErroAoCriarTransacao("Lazer em familia no McDonald's", valorInvalido, TipoTransacao.DESPESA, "McDonald's", msgEsperada);
    }

    @Test
    @DisplayName("Dado um tipo nulo, quando criar transação, então deve lançar uma exceção")
    void naoDeveCriarTransacaoComTipoNulo() {
        deveLancarErroAoCriarTransacao("Desc Válida", BigDecimal.TEN, null, "Benef Válido", MSG_TIPO_TRANSACAO_INVALIDA);
    }

    @ParameterizedTest
    @MethodSource("beneficiarioInvalido")
    @DisplayName("Dado um beneficiário inválido, quando criar transação, então deve lançar uma exceção")
    void naoDeveCriarTransacaoComBeneficiarioInvalido(String beneficiarioInvalido, String msgEsperada) {
        deveLancarErroAoCriarTransacao("Lazer em familia no McDonald's", new BigDecimal(150), TipoTransacao.DESPESA, beneficiarioInvalido, msgEsperada);
    }

}