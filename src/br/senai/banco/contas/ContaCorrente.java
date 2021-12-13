package br.senai.banco.contas;

import java.time.LocalDateTime;

public class ContaCorrente extends Conta {
    private double chequeEspecial;

    public ContaCorrente(String nome, String cpf, double rendaMensal, int conta, String agencia) {
        super(nome, cpf, rendaMensal, conta, agencia);
        this.chequeEspecial = (rendaMensal * 0.25);
        setLimite(0 - getChequeEspecial());
    }

    public double getChequeEspecial() {
        return chequeEspecial;
    }
}
