package br.senai.banco.contas;

public class ContaPoupanca extends Conta {
    private double rendimentoAnual;

    public ContaPoupanca(String nome, String cpf, double rendaMensal, int conta, String agencia, double rendimentoAnual) {
        super(nome, cpf, rendaMensal, conta, agencia);
        this.rendimentoAnual = rendimentoAnual;
    }

    public double calculaRendimentoMensal() {
        return (Math.pow((1 + rendimentoAnual/100), 1D/12) - 1) * 100;
    }

    public void simulacao(int meses) {
        StringBuilder retorno = new StringBuilder();
        if (meses > 0 && rendimentoAnual > 0) {
            double rendimentoMensal = calculaRendimentoMensal();
            double simulacao =  (Math.pow((1 + rendimentoMensal/100), meses/12D) - 1) * 100;
            retorno.append("Resultado para a simulação: \n")
                    .append("Mes(es): ").append(meses).append(" - Valor: R$").append(simulacao);
        } else {
            retorno.append("Valores inválidos");
        }
        System.out.println(retorno);
    }

    public double getRendimentoAnual() {
        return rendimentoAnual;
    }

    public void setRendimentoAnual(double rendimentoAnual) {
        this.rendimentoAnual = rendimentoAnual;
    }
}
