package br.com.mateussilva.financeiro.domain.model.usuario;

import br.com.mateussilva.financeiro.domain.model.builder.ContaBancariaBuilder;
import br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder;
import br.com.mateussilva.financeiro.domain.model.conta.Carteira;
import br.com.mateussilva.financeiro.domain.model.conta.Conta;
import br.com.mateussilva.financeiro.domain.model.conta.ContaBancaria;
import br.com.mateussilva.financeiro.domain.model.conta.TipoConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UsuarioTest {

    private static Usuario usuario;
    private Conta conta;

    @BeforeEach
    void setup() {
        usuario = UsuarioBuilder.umUsuario()
                .comNome("Mateus Silva")
                .comEmail("mateus@valido.com")
                .build();
    }

    static Stream<Arguments> nomesInvalidos() {
        return Stream.of (
                Arguments.of(null, Usuario.MSG_ERRO_NOME_INVALIDO),
                Arguments.of("", Usuario.MSG_ERRO_NOME_INVALIDO),
                Arguments.of("   ", Usuario.MSG_ERRO_NOME_INVALIDO)
        );
    }
    static Stream<Arguments> emailsInvalidos() {
        return Stream.of (
                Arguments.of(null, Usuario.MSG_ERRO_EMAIL_INVALIDO),
                Arguments.of("", Usuario.MSG_ERRO_EMAIL_INVALIDO),
                Arguments.of("   ", Usuario.MSG_ERRO_EMAIL_INVALIDO),

                Arguments.of("emailinvalido", Usuario.MSG_ERRO_EMAIL_FORMATO),
                Arguments.of("email@.com", Usuario.MSG_ERRO_EMAIL_FORMATO)
        );
    }

    private void deveLancarErroAoCriarUsuario(String nome, String email, String msgEsperada) {
        assertThatThrownBy(() -> UsuarioBuilder.umUsuario()
                .comNome(nome)
                .comEmail(email)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }
    private void deveLancarErroAoAlterar(Runnable acao, String msgEsperada) {
        assertThatThrownBy(acao::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }

    @Test
    @DisplayName("Dado dados válidos, quando criar usuário, então deve criar usuário com sucesso e criar lista de contas")
    void deveCriarUmUsuarioComDadosValidos() {
        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getNome()).isEqualTo("Mateus Silva");
        assertThat(usuario.getEmail()).isEqualTo("mateus@valido.com");

        assertThat(usuario.getContas()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Dado uma conta válida, quando adicionar à lista, então a lista deve conter a conta")
    void deveAdicionarUmaContaAoUsuario() {
        ContaBancaria novaConta = ContaBancariaBuilder.umaContaBancaria().build();

        usuario.adicionarConta(novaConta);

        assertThat(usuario.getContas())
                .hasSize(1)
                .contains(novaConta);
    }

    @Test
    @DisplayName("Dado uma conta nula, quando adicionar à lista, então deve lançar exceção")
    void naoDeveAdicionarContaNula() {
        assertThatThrownBy(() -> usuario.adicionarConta(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERRO: Uma conta válida deve ser informada.");
    }

    @Test
    @DisplayName("Quando obter a lista de contas, então deve retornar uma lista não-modificável")
    void deveRetornarListaNaoModificavel() {
        List<Conta> contas = usuario.getContas();
        assertThatThrownBy(() -> contas.add(ContaBancariaBuilder.umaContaBancaria().build()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @ParameterizedTest
    @MethodSource("nomesInvalidos")
    @DisplayName("Dado nome inválido, quando criar usuário, então deve lançar exceção")
    void naoDeveCriarUmUsuarioComNomeInvalido(String nomeInvalido, String msgEsperada) {
        deveLancarErroAoCriarUsuario(nomeInvalido, "mateus@valido.com", msgEsperada);
    }

    @Test
    @DisplayName("Dado usuário existente, quando alterar nome, então nome deve ser atualizado")
    void deveAlterarNomeDeUsuario() {
        usuario.alterarNome("Maria Alves");
        assertThat(usuario.getNome()).isEqualTo("Maria Alves");
    }

    @ParameterizedTest
    @MethodSource("nomesInvalidos")
    @DisplayName("Dado nome inválido, quando alterar nome, então deve lançar exceção")
    void naoDeveAlterarNomeDeUsuarioComNomeInvalido(String nomeInvalido, String msgEsperada) {
        deveLancarErroAoAlterar(() -> usuario.alterarNome(nomeInvalido), msgEsperada);
    }

    @ParameterizedTest
    @MethodSource("emailsInvalidos")
    @DisplayName("Dado email inválido, quando criar usuário, então deve lançar exceção")
    void naoDeveCriarUmUsuarioComEmailNulo(String emailInvalido, String msgEsperada) {
        deveLancarErroAoCriarUsuario("Mateus Silva", emailInvalido, msgEsperada);
    }

    @Test
    @DisplayName("Dado usuário existente, quando alterar email, então email deve ser atualizado")
    void deveAlterarEmailDeUsuario() {
        usuario.alterarEmail("mateus@teste.com.br");
        assertThat(usuario.getEmail()).isEqualTo("mateus@teste.com.br");
    }

    @ParameterizedTest
    @MethodSource("emailsInvalidos")
    @DisplayName("Dado email inválido, quando alterar email, então deve lançar exceção")
    void naoDeveAlterarEmailDeUsuarioComEmailInvalido(String emailInvalido, String msgEsperada) {
        deveLancarErroAoAlterar(() -> usuario.alterarEmail(emailInvalido), msgEsperada);
    }

}