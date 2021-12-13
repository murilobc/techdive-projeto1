package br.senai.banco.util;

import br.senai.banco.contas.Conta;
import br.senai.banco.contas.ContaCorrente;
import br.senai.banco.contas.ContaPoupanca;

import javax.swing.text.MaskFormatter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Valida {

    public static Conta validaConta(String nome, String cpf, String renda, String agencia, int codigo) {
        double rendaMensal;

        if (nome.isBlank()) {
            return null;
        }
        if (validarCpf(cpf)) {
            return null;
        }
        if (renda.isBlank()) {
            return null;
        } else {
            rendaMensal = Double.parseDouble(renda);
            if (rendaMensal <= 0.0) {
                return null;
            }
        }
        if (agencia.isBlank()) {
            return null;
        }
        return new ContaCorrente(nome, cpf, rendaMensal, codigo, agencia);
    }

    public static boolean validarCpf(String cpf) {
        return (cpf.isBlank() || cpf.replaceAll("[^0-9]", "").length() < 11);
    }

    public static ContaPoupanca validaContaPoupanca(Conta conta, String rendimentoAnual) {
        double rendimento;
        if (rendimentoAnual.isBlank()) {
            return null;
        } else {
            rendimento = Double.valueOf(rendimentoAnual);
            if (rendimento <= 0) {
                return null;
            }
        }

        return new ContaPoupanca(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(), conta.getAgencia(), rendimento);
    }

    public static String mascaraCpf(String cpf) {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("###.###.###-##");
            mask.setValueContainsLiteralCharacters(false);
            cpf = mask.valueToString(cpf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cpf;
    }

    public static String formatarReais(double valor) {
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return nf.format(valor);
    }
}
