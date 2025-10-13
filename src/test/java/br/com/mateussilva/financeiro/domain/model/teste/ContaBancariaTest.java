package br.com.mateussilva.financeiro.domain.model.teste;

import br.com.mateussilva.financeiro.domain.model.conta.Conta;
import br.com.mateussilva.financeiro.domain.model.conta.ContaBancaria;
import br.com.mateussilva.financeiro.domain.model.conta.TipoConta;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;
import br.com.mateussilva.financeiro.domain.model.builder.ContaBancariaBuilder;
import br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static br.com.mateussilva.financeiro.domain.model.conta.Conta.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContaBancariaTest {

    private Usuario usuario;
    private ContaBancaria contaPadrao;

    @BeforeEach
    void setUp() {
        usuario = UsuarioBuilder.umUsuario().build();

        contaPadrao = ContaBancariaBuilder.umaContaBancaria()
                .comUsuario(usuario)
                .comSaldo(BigDecimal.ZERO)
                .build();
    }


    private ContaBancaria novaContaComSaldo(BigDecimal saldo) {
        return ContaBancariaBuilder.umaContaBancaria().comSaldo(saldo).build();
    }

    private void deveLancarErroAoCriarContaBancaria(String nome, String agencia, String conta, String msgEsperada) {
        assertThatThrownBy(() -> ContaBancariaBuilder.umaContaBancaria()
                .comNome(nome)
                .comUsuario(usuario)
                .comAgencia(agencia)
                .comConta(conta)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }
    private void deveLancarErroAoRealizarOperacoes(Runnable acao, String msgEsperada) {
        assertThatThrownBy(acao::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }


    static Stream<Arguments> nomesContasInvalidas() {
        return Stream.of(
                Arguments.of(null, ContaBancaria.MSG_ERRO_NOME_INVALIDO),
                Arguments.of("", ContaBancaria.MSG_ERRO_NOME_INVALIDO),
                Arguments.of("   ", ContaBancaria.MSG_ERRO_NOME_INVALIDO)
        );
    }
    static Stream<Arguments> numerosContasInvalidas() {
        return Stream.of(
                Arguments.of(null, ContaBancaria.MSG_ERRO_CONTA_INVALIDO),
                Arguments.of("", ContaBancaria.MSG_ERRO_CONTA_INVALIDO),
                Arguments.of("   ", ContaBancaria.MSG_ERRO_CONTA_INVALIDO),
                Arguments.of("B1234A", ContaBancaria.MSG_ERRO_CONTA_INVALIDO),
                Arguments.of("BDOAYCA", ContaBancaria.MSG_ERRO_CONTA_INVALIDO)
        );
    }
    static Stream<Arguments> numerosAgenciaInvalidas() {
        return Stream.of(
                Arguments.of(null, ContaBancaria.MSG_ERRO_AGENCIA_INVALIDO),
                Arguments.of("", ContaBancaria.MSG_ERRO_AGENCIA_INVALIDO),
                Arguments.of("   ", ContaBancaria.MSG_ERRO_AGENCIA_INVALIDO),
                Arguments.of("B1234A", ContaBancaria.MSG_ERRO_AGENCIA_INVALIDO),
                Arguments.of("BDOAYCA", ContaBancaria.MSG_ERRO_AGENCIA_INVALIDO)
        );
    }

    static Stream<Arguments> valoresValidosParaOperacoes() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(0.01)),
                Arguments.of(BigDecimal.valueOf(10)),
                Arguments.of(BigDecimal.valueOf(50)),
                Arguments.of(BigDecimal.valueOf(100)),
                Arguments.of(BigDecimal.valueOf(1000)),
                Arguments.of(BigDecimal.valueOf(5000))
        );
    }
    static Stream<Arguments> valoresInvalidosParaOperacoes() {
        return Stream.of(
                Arguments.of(null, MSG_VALOR_NULL),
                Arguments.of(BigDecimal.ZERO, MSG_VALOR_INVALIDO),
                Arguments.of(new BigDecimal("-0.01"), MSG_VALOR_INVALIDO),
                Arguments.of(BigDecimal.valueOf(-10), MSG_VALOR_INVALIDO),
                Arguments.of(BigDecimal.valueOf(-100), MSG_VALOR_INVALIDO)
        );
    }
    static Stream<Arguments> valoresInvalidosParaSaque() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(500), MSG_SALDO_INSUFICIENTE),
                Arguments.of(BigDecimal.valueOf(101), MSG_SALDO_INSUFICIENTE),
                Arguments.of(BigDecimal.valueOf(1000), MSG_SALDO_INSUFICIENTE)
        );
    }


    @Test
    @DisplayName("Dado uma conta com saldo zero, deve inicializar corretamente todos os campos")
    void deveCriarContaBancariaComSaldoZero() {
        ContaBancaria conta = ContaBancariaBuilder.umaContaBancaria()
                .comNome("Conta Corrente Itaú")
                .comUsuario(usuario)
                .comAgencia("1234")
                .comConta("123456")
                .comSaldo(BigDecimal.ZERO)
                .comTipo(TipoConta.CORRENTE)
                .build();

        assertThat(conta.getId()).isNotNull();
        assertThat(conta.getNome()).isEqualTo("Conta Corrente Itaú");
        assertThat(conta.getUsuario()).isNotNull();
        assertThat(conta.getAgencia()).isEqualTo("1234");
        assertThat(conta.getConta()).isEqualTo("123456");
        assertThat(conta.getSaldo()).isEqualTo(BigDecimal.ZERO);
        assertThat(conta.getTipo()).isEqualTo(TipoConta.CORRENTE);
    }

    @ParameterizedTest
    @MethodSource("nomesContasInvalidas")
    @DisplayName("Dado um nome inválido, não deve criar conta bancária")
    void naoDeveCriarContaBancariaComNomeInvalido(String nomeInvalido, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria(nomeInvalido, "1234", "123456", msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("numerosAgenciaInvalidas")
    @DisplayName("Dado uma agência inválida, não deve criar conta bancária")
    void naoDeveCriarContaBancariaComAgenciaInvalida(String agenciaInvalida, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria("Conta Corrente Itaú", agenciaInvalida, "123456", msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("numerosContasInvalidas")
    @DisplayName("Dado um número de conta inválido, não deve criar conta bancária")
    void naoDeveCriarContaBancariaComContaInvalida(String contaInvalida, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria("Conta Corrente Itaú", "1234", contaInvalida, msgEsperada);
    }

    @Test
    @DisplayName("Dado um tipo de conta nulo, deve lançar exceção ao criar conta")
    void naoDeveCriarContaBancariaComTipoNulo() {
        assertThatThrownBy(() -> ContaBancariaBuilder.umaContaBancaria().comTipo(null).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ContaBancaria.MSG_ERRO_TIPO_CONTA_INVALIDO);
    }

    @ParameterizedTest
    @MethodSource("valoresValidosParaOperacoes")
    @DisplayName("Dado um valor válido, deve realizar depósito corretamente")
    void deveDepositarUmValorValido(BigDecimal valorValido) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        conta.depositar(valorValido);
        assertThat(conta.getSaldo()).isEqualTo(valorValido);
    }

    @ParameterizedTest
    @MethodSource("valoresValidosParaOperacoes")
    @DisplayName("Dado um valor válido, deve realizar saque corretamente")
    void deveSacarUmValorValido(BigDecimal valorValido) {
        BigDecimal saldoInicial = new BigDecimal(5000);
        ContaBancaria conta = novaContaComSaldo(saldoInicial);
        conta.sacar(valorValido);
        assertThat(conta.getSaldo()).isEqualTo(saldoInicial.subtract(valorValido));
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaOperacoes")
    @DisplayName("Dado um valor inválido (nulo, negativo ou zero), deve lançar exceção ao depositar")
    void deveLancarExcecaoAoDepositarValorInvalido(BigDecimal valorInvalido, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        deveLancarErroAoRealizarOperacoes(() -> conta.depositar(valorInvalido), msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaOperacoes")
    @DisplayName("Dado um valor inválido (nulo, negativo ou zero), deve lançar exceção ao sacar")
    void deveLancarExcecaoAoSacarValorInvalido(BigDecimal valorInvalido, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        deveLancarErroAoRealizarOperacoes(() -> conta.sacar(valorInvalido), msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaSaque")
    @DisplayName("Dado um valor maior que o saldo, deve lançar exceção ao sacar")
    void deveLancarExcecaoAoSacarValorMaiorQueSaldo(BigDecimal valorSaque, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(new BigDecimal(100));
        deveLancarErroAoRealizarOperacoes(() -> conta.sacar(valorSaque), msgEsperada);
    }
}
