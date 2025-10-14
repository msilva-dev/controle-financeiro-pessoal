package br.com.mateussilva.financeiro.domain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ValidadorUtilTest {

    static Stream<Arguments> emailsValidos() {
        return Stream.of(
                Arguments.of("mateus@valido.com"),
                Arguments.of("teste@email.com.br"),
                Arguments.of("usuario123@empresa.org"),
                Arguments.of("nome.sobrenome@dominio.tech"),
                Arguments.of("user+test@sub.dominio.io")
        );
    }
    static Stream<Arguments> emailsInvalidos() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of("@email.com.br"),
                Arguments.of("emailInvalido.com.br"),
                Arguments.of("mariainvalido@"),
                Arguments.of("sem-arroba-e-ponto")
        );
    }

    @ParameterizedTest
    @MethodSource("emailsValidos")
    @DisplayName("Dado emails válidos, quando validar, deve retornar true")
    void validarEmailComSucesso(String emailValido) {
        assertThat(ValidadorUtil.isEmailValido(emailValido)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("emailsInvalidos")
    @DisplayName("Dado email inválido, quando validar, deve retornar false")
    void naoDeveValidarEmail(String emailInvalido) {
        assertThat(ValidadorUtil.isEmailValido(emailInvalido)).isFalse();
    }

}