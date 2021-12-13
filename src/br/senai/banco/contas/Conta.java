package br.senai.banco.contas;

import br.senai.banco.util.Valida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private String nome;
    private String cpf;
    private double rendaMensal;
    private int conta;
    private String agencia;
    private double saldo;
    private double limite;
    private List<Transacao> transacoes;

    public Conta(String nome, String cpf, double rendaMensal, int conta, String agencia) {
        this.nome = nome;
        this.cpf = cpf;
        this.rendaMensal = rendaMensal;
        this.conta = conta;
        this.agencia = agencia;
        this.limite = 0D;
        this.transacoes = new ArrayList<Transacao>();
    }

    public void saque(double valor) {
        StringBuilder retorno = new StringBuilder();
        if (valor > 0 && verificaLimite(valor)) {
            this.saldo -= valor;
            registro(getConta(), 0, valor, LocalDateTime.now(), Transacao.TRANSACAO_SAQUE, getSaldo());
            retorno.append("Saque efetuado: R$").append(valor).append(" - Saldo: ").append(getSaldo());
        } else {
            retorno.append("Saldo insuficiente");
        }
        System.out.println(retorno);
    }

    public void deposito(double valor) {
        StringBuilder retorno = new StringBuilder();
        if (valor > 0 ) {
            this.saldo += valor;
            registro(0, getConta(), valor, LocalDateTime.now(), Transacao.TRANSACAO_DEPOSITO, getSaldo());
            retorno.append("Depósito efetuado: R$").append(valor).append(" - Saldo: ").append(getSaldo());
        } else {
            retorno.append("Valor inválido");
        }
        System.out.println(retorno);
    }

    public void saldo() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Saldo: ").append(getSaldo());
        System.out.println(retorno);
    }

    public void registro(int origem, int destino, double valor, LocalDateTime data, String tipo, double saldo) {
        Transacao t = new Transacao(origem, destino, valor, data, tipo, saldo);
        getTransacoes().add(t);
    }

    public void extrato() {
        if (!getTransacoes().isEmpty()) {
            for (Transacao t:getTransacoes()) {
                System.out.println(t.toString());
            }
        }
    }

    public void transferir(Conta conta, double valor) {
        StringBuilder retorno = new StringBuilder();
        if (valor > 0) {
            if (verificaLimite(valor)) {
                setSaldo(getSaldo() - valor);
                conta.setSaldo(conta.getSaldo() + valor);

                registro(getConta(), conta.getConta(), valor, LocalDateTime.now(), Transacao.TRANSACAO_TRANSFERENCIA, getSaldo());
                conta.registro(getConta(), conta.getConta(), valor, LocalDateTime.now(), Transacao.TRANSACAO_TRANSFERENCIA, conta.getSaldo());

                retorno.append("Tranferência: R$").append(valor).append(" para conta: ").append(conta.getConta()).append(", Realizada");
            } else {
                retorno.append("Limite insuficiente");
            }
        } else {
            retorno.append("Valor inválido");
        }
        System.out.println(retorno);
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Conta: ").append(getConta()).append(", Agencia:").append(getAgencia()).append(", CPF: ").append(Valida.mascaraCpf(getCpf()))
                .append(", Nome: ").append(getNome()).append(", Renda: ").append(Valida.formatarReais(getRendaMensal())).append(", Saldo: ").append(Valida.formatarReais(getSaldo()));
        return retorno.toString();
    }

    private boolean verificaLimite(double valor)  {
        return (getSaldo() + getLimite()) - valor >= 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}
