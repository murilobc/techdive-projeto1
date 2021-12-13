package br.senai.banco.contas;

public class ContaInvestimento extends Conta {
    public static final int INVESTIMENTO_TESOURO_DIRETO = 0;
    public static final int INVESTIMENTO_CDB = 1;
    public static final int INVESTIMENTO_FUNDO_DI = 2;

    public static double rendimentos[] = {6.5, 9.0, 11.2};

    private int investimento;

    public ContaInvestimento(String nome, String cpf, double rendaMensal, int conta, String agencia, int investimento) {
        super(nome, cpf, rendaMensal, conta, agencia);
        this.investimento = investimento;
    }

    public void simulacao(int investimento) {
        StringBuilder retorno = new StringBuilder();
        if (validaInvestimento(investimento)) {
            double simulacao =  getSaldo() * (rendimentos[investimento]/100 + 1);
            retorno.append("Resultado para a simulação: \n")
                    .append("Mes(es): ").append(12).append(" - Valor: R$").append(simulacao);
        } else {
            retorno.append("Valores inválidos");
        }
        System.out.println(retorno);
    }

    public boolean validaInvestimento(int investimento) {
        return investimento >= 0 && investimento < rendimentos.length;
    }
}
