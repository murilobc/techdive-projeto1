package br.senai.banco.contas;

import java.time.LocalDateTime;

public class Transacao {
    public static final String TRANSACAO_SAQUE = "S";
    public static final String TRANSACAO_DEPOSITO = "D";
    public static final String TRANSACAO_TRANSFERENCIA = "T";

    private int origem;
    private int destino;
    private double valor;
    private LocalDateTime data;
    private String tipo;
    private double saldo;

    public Transacao(int origem, int destino, double valor, LocalDateTime data, String tipo, double saldo) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Transação: ");

        switch (this.tipo) {
            case TRANSACAO_SAQUE:
                retorno.append("SAQUE").append(" Origem: ").append(this.origem);
                break;
            case TRANSACAO_DEPOSITO:
                retorno.append("DEPOSITO").append(" Destino: ").append(this.destino);
                break;
            case TRANSACAO_TRANSFERENCIA:
                retorno.append("TRANSFERENCIA").append(" Origem: ").append(this.origem)
                        .append(", Destino: ").append(this.destino);
        }

        retorno.append(" Valor: R$").append(this.valor)
                .append(", Data: ").append(this.data)
                .append(", Saldo: ").append(this.saldo);

        return retorno.toString();
    }
}
