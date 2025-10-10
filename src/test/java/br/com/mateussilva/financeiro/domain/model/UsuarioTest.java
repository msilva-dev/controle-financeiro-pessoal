package br.com.mateussilva.financeiro.domain.model;

import br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UsuarioTest {

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

    @Test
    @DisplayName("Deve criar um usuário com dados válidos com sucesso")
    void deveCriarUmUsuarioComDadosValidos() {
        Usuario usuario = UsuarioBuilder.umUsuario()
                .comNome("Mateus")
                .comEmail("mateus@valido.com")
                .build();

        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getNome()).isEqualTo("Mateus");
        assertThat(usuario.getEmail()).isEqualTo("mateus@valido.com");
    }

    @ParameterizedTest
    @MethodSource("nomesInvalidos")
    @DisplayName("Não deve criar um usuário com nome inválido")
    void naoDeveCriarUmUsuarioComNomeNulo(String nomeInvalido, String msgEsperada) {
        assertThatThrownBy(() -> UsuarioBuilder.umUsuario().comNome(nomeInvalido).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }

    @Test
    @DisplayName("Deve alterar o nome de usuário")
    void deveAlterarNomeDeUsuario() {
        Usuario usuario = UsuarioBuilder.umUsuario().comNome("Maria Alves").build();
        String novoNome = "Mateus Silva";
        usuario.alterarNome(novoNome);
        assertThat(usuario.getNome()).isEqualTo(novoNome);
    }

    @ParameterizedTest
    @MethodSource("nomesInvalidos")
    @DisplayName("Não deve alterar nome de usuário com nome inválido")
    void naoDeveAlterarNomeDeUsuarioComNomeInvalido(String nomeInvalido, String msgEsperada) {
        Usuario usuario = UsuarioBuilder.umUsuario().build();
        assertThatThrownBy(() -> usuario.alterarNome(nomeInvalido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);

    }

    @ParameterizedTest
    @MethodSource("emailsInvalidos")
    @DisplayName("Não deve criar um usuário com email inválido")
    void naoDeveCriarUmUsuarioComEmailNulo(String emailInvalido, String msgEsperada) {
        assertThatThrownBy(() -> UsuarioBuilder.umUsuario().comEmail(emailInvalido).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);
    }

    @Test
    @DisplayName("Deve alterar o email de usuário")
    void deveAlterarEmailDeUsuario() {
        Usuario usuario = UsuarioBuilder.umUsuario().comEmail("maria@valido.com").build();
        String novoEmail = "mateus@teste.com.br";
        usuario.alterarEmail(novoEmail);
        assertThat(usuario.getEmail()).isEqualTo(novoEmail);
    }

    @ParameterizedTest
    @MethodSource("emailsInvalidos")
    @DisplayName("Não deve alterar email de usuário com email inválido")
    void naoDeveAlterarEmailDeUsuarioComEmailInvalido(String emailInvalido, String msgEsperada) {
        Usuario usuario = UsuarioBuilder.umUsuario().build();
        assertThatThrownBy(() -> usuario.alterarEmail(emailInvalido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msgEsperada);

    }


}