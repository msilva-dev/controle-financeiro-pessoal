package br.com.mateussilva.financeiro.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class MainTest {

    @Test
    void deveExecutarMetodoMainSemErros() {
        assertThatCode(() -> Main.main(new String[]{}))
                .doesNotThrowAnyException();
    }

}