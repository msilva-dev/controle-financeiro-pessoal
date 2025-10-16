package br.com.mateussilva.financeiro.domain.model.conta;

import br.com.mateussilva.financeiro.domain.model.transacao.DadosNovaTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.TipoTransacao;
import br.com.mateussilva.financeiro.domain.model.transacao.Transacao;
import br.com.mateussilva.financeiro.domain.model.usuario.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Conta implements ContaOperacoes{

    public static final String MSG_ERRO_NOME_INVALIDO = "ERRO: Nome não pode ser nulo ou vazio.";
    public static final String MSG_SALDO_INSUFICIENTE = "ERRO: Saldo insuficiente para realizar saque.";
    public static final String MSG_VALOR_INVALIDO = "ERRO: Valor da operação deve ser maior que zero.";
    public static final String MSG_VALOR_NULL = "ERRO: Valor do depósito não pode ser nulo.";


    private final UUID id;
    private final String nome;
    private BigDecimal saldo;
    private final Usuario usuario;
    private final List<Transacao> transacoes;


    public Conta(String nome, Usuario usuario) {
        validarNome(nome);
        validarUsuario(usuario);

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.saldo = BigDecimal.ZERO;
        this.usuario = usuario;
        this.transacoes = new ArrayList<>();
    }


    @Override
    public void sacar(DadosNovaTransacao dados) {
        validarSaque(dados.valor());
        this.saldo = this.saldo.subtract(dados.valor());
        registrarTransacao(dados, TipoTransacao.DESPESA);
    }
    @Override
    public void depositar(DadosNovaTransacao dados) {
        validarDeposito(dados.valor());
        this.saldo = this.saldo.add(dados.valor());
        registrarTransacao(dados, TipoTransacao.RECEITA);
    }
    @Override
    public List<Transacao> getExtrato() {
        return Collections.unmodifiableList(this.transacoes);
    }


    public UUID getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    protected void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isBlank()) {
            throw new IllegalArgumentException(MSG_ERRO_NOME_INVALIDO);
        }
    }
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
    }
    private void validarSaque(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException(MSG_VALOR_NULL);
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MSG_VALOR_INVALIDO);
        }
        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException(MSG_SALDO_INSUFICIENTE);
        }
    }
    private void validarDeposito(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException(MSG_VALOR_NULL);
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MSG_VALOR_INVALIDO);
        }
    }
    private void registrarTransacao(DadosNovaTransacao dados, TipoTransacao tipo) {
        Transacao transacao = new Transacao(
                dados.descricao(),
                dados.valor(),
                tipo,
                dados.beneficiario());
        this.transacoes.add(transacao);
    }

}
