package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.builder.CarteiraBuilder;
import br.com.mateussilva.financeiro.domain.model.builder.UsuarioBuilder;
import br.com.mateussilva.financeiro.domain.model.transacao.DadosNovaTransacao;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.mateussilva.financeiro.domain.model.factory.DadosNovaTransacaoFactory.umDepositoComValor;
import static br.com.mateussilva.financeiro.domain.model.factory.DadosNovaTransacaoFactory.umSaqueComValor;
import static org.assertj.core.api.Assertions.assertThat;

class CarteiraTest {

    private Usuario usuario;
    private Carteira carteira;

    @BeforeEach
    void setUp(){
        usuario = UsuarioBuilder.umUsuario().build();
        carteira = CarteiraBuilder.umaCarteira().comUsuario(usuario).build();
    }

    @Test
    @DisplayName("Dado dados válidos, quando criar carteira, então deve criar com sucesso")
    void criarCarteiraComSucesso() {
        assertThat(carteira.getNome()).isEqualTo("Carteira");
        assertThat(usuario).isNotNull();
        assertThat(carteira.getSaldo()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Deve depositar e sacar da carteira corretamente")
    void deveDepositarESacarCorretamente() {
        DadosNovaTransacao deposito = umDepositoComValor(new BigDecimal("50.00"));
        DadosNovaTransacao saque = umSaqueComValor(new BigDecimal("20.00"));

        carteira.depositar(deposito);
        carteira.sacar(saque);

        assertThat(carteira.getSaldo()).isEqualByComparingTo("30.00");
        assertThat(carteira.getExtrato()).hasSize(2);
    }

}