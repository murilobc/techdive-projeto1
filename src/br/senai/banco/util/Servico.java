package br.senai.banco.util;

import br.senai.banco.contas.Conta;
import br.senai.banco.contas.ContaCorrente;
import br.senai.banco.contas.ContaInvestimento;
import br.senai.banco.contas.ContaPoupanca;

import java.util.List;

public class Servico {

    public int gerarCodigo(List<Conta> contas) {
        if (contas.isEmpty()) {
            return 10000;
        } else {
            return contas.get(contas.size() - 1).getConta() + 1;
        }
    }

    public Conta buscarConta(int conta, List<Conta> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (conta == contas.get(i).getConta()) {
                return contas.get(i);
            }
        }
        return null;
    }

    public void salvarConta(Conta c, List<Conta> contas) {

        if (c.getConta() > 0) {
            contas.add(c);
        } else {
            for (int i = 0; i < contas.size(); i++) {
                if (contas.get(i).getConta() == c.getConta()) {
                    contas.get(i).setNome(c.getNome());
                    contas.get(i).setAgencia(c.getAgencia());
                    contas.get(i).setRendaMensal(c.getRendaMensal());
                }
            }
        }

    }



    public void listarContas(String opcao, List<Conta> contas) {
        switch (opcao) {
            case "cc":
                listarCC(contas);
                break;
            case "cp":
                listarCP(contas);
                break;
            case "ci":
                listarCI(contas);
                break;
        }
    }

    private void listarCC(List<Conta> contas) {
        System.out.println("Lista de Contas Correntes: ");
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i) instanceof ContaCorrente) {
                System.out.println(contas.get(i));
            }
        }
    }
    private void listarCP(List<Conta> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i) instanceof ContaPoupanca) {
                System.out.println(contas.get(i));
            }
        }
    }
    private void listarCI(List<Conta> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i) instanceof ContaInvestimento) {
                System.out.println(contas.get(i));
            }
        }
    }

    public void listarContasNegativas(List<Conta> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getSaldo() > 0) {
                System.out.println(contas.get(i));
            }
        }
    }

    public void totalInvestido(List<Conta> contas) {
        double total = 0D;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i) instanceof ContaInvestimento) {
                total += contas.get(i).getSaldo();
            }
        }
        System.out.println("Total de investimentos: R$" + Valida.formatarReais(total));
    }

    public void totalTransacoes(int cliente, List<Conta> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getConta() == cliente) {
                for (int j = 0; j < contas.get(i).getTransacoes().size(); j++) {
                    System.out.println(contas.get(i).getTransacoes().get(j));
                }
            }
        }
    }
}
