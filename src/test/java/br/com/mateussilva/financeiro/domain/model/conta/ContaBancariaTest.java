package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.builder.ContaBancariaBuilder;
import br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder;
import br.com.mateussilva.financeiro.domain.model.transacao.DadosNovaTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.TipoTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.Transacao;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static br.com.mateussilva.financeiro.domain.model.conta.Conta.*;
import static br.com.mateussilva.financeiro.domain.model.factory.DadosNovaTransacaoFactory.*;
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

    private void deveLancarErroAoCriarContaBancaria(String nome, String agencia,
                                                    String conta, String msgEsperada) {
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
    private void validarDadosDoExtrato(Transacao transacao, DadosNovaTransacao dadosOriginais) {
        assertThat(transacao.valor()).isEqualByComparingTo(dadosOriginais.valor());
        assertThat(transacao.descricao()).isEqualTo(dadosOriginais.descricao());
        assertThat(transacao.beneficiario()).isEqualTo(dadosOriginais.beneficiario());
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
                Arguments.of(new BigDecimal("0.01")),
                Arguments.of(new BigDecimal("10.00")),
                Arguments.of(new BigDecimal("50.00")),
                Arguments.of(new BigDecimal("100.00")),
                Arguments.of(new BigDecimal("1000.00")),
                Arguments.of(new BigDecimal("5000.00"))
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
    @DisplayName("Dado conta com saldo zero, quando criar conta, então deve inicializar todos os campos")
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

    @Test
    @DisplayName("Dado uma conta, quando criar, deve iniciar uma lista de transações vazia")
    void deveIniciarListaDeTransacoesVazia() {
        assertThat(contaPadrao.getExtrato()).isEmpty();
    }

    @Test
    @DisplayName("Dado um usuário nulo, quando criar conta, então deve lançar exceção")
    void naoDeveCriarContaComUsuarioNulo() {
        assertThatThrownBy(() -> ContaBancariaBuilder.umaContaBancaria()
                .comUsuario(null)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Usuário não pode ser nulo."); // Ajuste a mensagem se for diferente
    }

    @ParameterizedTest
    @MethodSource("nomesContasInvalidas")
    @DisplayName("Dado nome inválido, quando criar conta, então deve lançar exceção")
    void naoDeveCriarContaBancariaComNomeInvalido(String nomeInvalido, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria(nomeInvalido, "1234", "123456", msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("numerosAgenciaInvalidas")
    @DisplayName("Dado agência inválida, quando criar conta, então deve lançar exceção")
    void naoDeveCriarContaBancariaComAgenciaInvalida(String agenciaInvalida, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria("Conta Corrente Itaú", agenciaInvalida, "123456", msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("numerosContasInvalidas")
    @DisplayName("Dado número de conta inválido, quando criar conta, então deve lançar exceção")
    void naoDeveCriarContaBancariaComContaInvalida(String contaInvalida, String msgEsperada) {
        deveLancarErroAoCriarContaBancaria("Conta Corrente Itaú", "1234", contaInvalida, msgEsperada);
    }

    @Test
    @DisplayName("Dado tipo de conta nulo, quando criar conta, então deve lançar exceção")
    void naoDeveCriarContaBancariaComTipoNulo() {
        assertThatThrownBy(() -> ContaBancariaBuilder.umaContaBancaria().comTipo(null).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ContaBancaria.MSG_ERRO_TIPO_CONTA_INVALIDO);
    }

    @ParameterizedTest
    @MethodSource("valoresValidosParaOperacoes")
    @DisplayName("Dado valor válido, quando depositar, então deve atualizar saldo corretamente e registrar transação")
    void deveDepositarUmValorValido(BigDecimal valorValido) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        DadosNovaTransacao dadosDeposito = umDepositoComValor(valorValido);

        conta.depositar(dadosDeposito);

        assertThat(conta.getSaldo()).isEqualByComparingTo(valorValido);

        assertThat(conta.getExtrato()).hasSize(1);

        Transacao transacaoRegistrada = conta.getExtrato().getFirst();
        validarDadosDoExtrato(transacaoRegistrada, dadosDeposito);
        assertThat(transacaoRegistrada.tipo()).isEqualTo(TipoTransacao.RECEITA);
    }

    @ParameterizedTest
    @MethodSource("valoresValidosParaOperacoes")
    @DisplayName("Dado valor válido, quando sacar, então deve atualizar saldo corretamente e registrar transação")
    void deveSacarUmValorValido(BigDecimal valorValido) {
        BigDecimal saldoInicial = new BigDecimal("5000");
        ContaBancaria conta = novaContaComSaldo(saldoInicial);
        DadosNovaTransacao dadosSaque = umSaqueComValor(valorValido);

        conta.sacar(dadosSaque);

        assertThat(conta.getSaldo()).isEqualByComparingTo(saldoInicial.subtract(valorValido));

        assertThat(conta.getExtrato()).hasSize(1);

        Transacao transacaoRegistrada = conta.getExtrato().getFirst();
        validarDadosDoExtrato(transacaoRegistrada, dadosSaque);
        assertThat(transacaoRegistrada.tipo()).isEqualTo(TipoTransacao.DESPESA);
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaOperacoes")
    @DisplayName("Dado valor inválido, quando depositar, então deve lançar exceção")
    void deveLancarExcecaoAoDepositarValorInvalido(BigDecimal valorInvalido, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        deveLancarErroAoRealizarOperacoes(() -> conta.depositar(umDepositoComValor(valorInvalido)), msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaOperacoes")
    @DisplayName("Dado valor inválido, quando sacar, então deve lançar exceção")
    void deveLancarExcecaoAoSacarValorInvalido(BigDecimal valorInvalido, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(BigDecimal.ZERO);
        deveLancarErroAoRealizarOperacoes(() -> conta.sacar(umSaqueComValor(valorInvalido)), msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("valoresInvalidosParaSaque")
    @DisplayName("Dado valor maior que o saldo, quando sacar, então deve lançar exceção")
    void deveLancarExcecaoAoSacarValorMaiorQueSaldo(BigDecimal valorSaque, String msgEsperada) {
        ContaBancaria conta = novaContaComSaldo(new BigDecimal(100));
        deveLancarErroAoRealizarOperacoes(() -> conta.sacar(umSaqueComValor(valorSaque)), msgEsperada);
    }
}
