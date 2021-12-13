package br.senai.banco;

import br.senai.banco.contas.Conta;
import br.senai.banco.contas.ContaCorrente;
import br.senai.banco.contas.ContaInvestimento;
import br.senai.banco.contas.ContaPoupanca;
import br.senai.banco.util.Const;
import br.senai.banco.util.Servico;
import br.senai.banco.util.Valida;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banco {
    Scanner input = new Scanner(System.in);
    List<Conta> contas = new ArrayList<Conta>();
    Servico servico = new Servico();


    public void inicio() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Se identifique: \n")
                .append("1 - Você é nosso funcionário \n")
                .append("2 - Você é nosso cliente\n");
        System.out.println(retorno);

        String opcao = input.nextLine();
        if (!opcao.isBlank()) {
            if (opcao.equalsIgnoreCase(Const.OPCAO_FUNCIONARIO)) {
                menuFuncionário();
            } else if (opcao.equalsIgnoreCase(Const.OPCAO_CLIENTE)) {
                menuCliente();
            } else {
                System.out.println("Opção inválida");
            }
        } else {
            System.out.println("Valor inválido");
        }

    }

    private void menuCliente() {
        StringBuilder retorno = new StringBuilder();
        System.out.println("Primeiro informe sua conta");
        String codigo = input.nextLine();
        Conta conta = servico.buscarConta(Integer.valueOf(codigo), contas);
        retorno.append("Escolha a opção\n")
                .append("1 - Saque\n")
                .append("2 - Saldo\n")
                .append("3 - Extrato\n")
                .append("4 - Transferência\n")
                .append("6 - Depósito\n")
                .append("7 - Simular Poupança\n")
                .append("0 - Retornar ao menu anterior");
        System.out.println(retorno);

        String opcao = input.nextLine();
        switch (opcao) {
            case Const.OPCAO_SAQUE:
                efetuarSaque(conta);
                break;
            case Const.OPCAO_SALDO:
                efetuarSaldo(conta);
                break;
            case Const.OPCAO_EXTRATO:
                efetuarExtrato(conta);
                break;
            case Const.OPCAO_TRASNFERENCIA:
                efetuarTransferencia(conta);
                break;
            case Const.OPCAO_DEPOSITO:
                efetuarDeposito(conta);
            case Const.OPCAO_SIMULAR:
                efetuarSimulacao(conta);
                break;
            default:
                inicio();
        }
    }

    private void efetuarSimulacao(Conta conta) {
        if (!(conta instanceof ContaPoupanca)) {
            System.out.println("Essa conta não permite simulação");
            menuCliente();
        } else {
            System.out.println("Informe o número de meses");
            String valor = input.nextLine();
            int v = 0;
            if (!valor.isBlank()) {
                v = Integer.valueOf(valor);
            }
            ((ContaPoupanca) conta).simulacao(v);
        }
    }

    private void efetuarDeposito(Conta conta) {
        System.out.println("Informe o valor do depósito");
        String valor = input.nextLine();
        double v = 0D;
        if (!valor.isBlank()) {
            v = Double.valueOf(valor);
        }
        conta.deposito(v);
    }

    private void efetuarTransferencia(Conta conta) {
        System.out.println("Informe o valor do saque: ");
        String valor = input.nextLine();
        double v = 0D;
        if (!valor.isBlank()) {
            v = Double.valueOf(valor);
        }
        System.out.println("Informe a conta de destino: ");
        String contaDestino = input.nextLine();
        int codigo = 0;
        if (!contaDestino.isBlank()) {
            codigo = Integer.valueOf(contaDestino);
        }

        conta.transferir(servico.buscarConta(codigo, contas), v);

    }

    private void efetuarExtrato(Conta conta) {
        conta.extrato();
    }

    private void efetuarSaldo(Conta conta) {
        conta.saldo();
    }

    private void efetuarSaque(Conta conta) {
        System.out.println("Informe o valor do saque");
        String valor = input.nextLine();
        double v = 0D;
        if (!valor.isBlank()) {
            v = Integer.valueOf(valor);
        }
        conta.saque(v);
    }

    public void menuFuncionário() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Escolha uma opção\n")
                .append("1 - Criar uma nova conta\n")
                .append("2 - Alterar dados cadastrais\n")
                .append("3 - Relatórios\n")
                .append("0 - Retornar ao menu anterior\n");
        System.out.println(retorno);

        String opcao = input.nextLine();
        if (!opcao.isBlank()) {
            switch (opcao) {
                case Const.OPCAO_RETORNO:
                    inicio();
                    break;
                case Const.OPCAO_CRIAR:
                    menuCriarConta();
                    break;
                case Const.OPCAO_ALTERAR:
                    menuAlterarConta();
                    break;
                case Const.OPCAO_RELATORIO:
                    menuRelatorios();
                    break;
            }
        }
    }

    private void menuRelatorios() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Qual o tipo de relatório desejado:\n")
                .append("1 - Lista contas\n")
                .append("2 - Contas negativadas\n")
                .append("3 - Total de valor investido\n")
                .append("4 - Total de transações\n")
                .append("0 - Retornar");
        System.out.println(retorno);

        String opcao = input.nextLine();
        switch (opcao) {
            case Const.OPCAO_LISTAR_CONTAS:
                listarContas();
                break;
            case Const.OPCAO_CONTAS_NEGATIVAS:
                servico.listarContasNegativas(contas);
                break;
            case Const.OPCAO_TOTAL_INVESTIDO:
                servico.totalInvestido(contas);
                break;
            case Const.OPCAO_TOTAL_TRANSACOES:
                System.out.println("Informe o codigo da conta");
                String codigo = input.nextLine();
                servico.totalTransacoes(Integer.valueOf(codigo), contas);
                break;
            default:
                menuFuncionário();
        }
    }

    public void listarContas() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Opções:\n")
                .append("1 - Listar Contas Corrente\n")
                .append("2 - Listar Contas Poupança\n")
                .append("3 - Listar Contas Investimento\n")
                .append("4 - Retornar");
        System.out.println(retorno);

        String opcao = input.nextLine();
        switch (opcao) {
            case "1":
                servico.listarContas(Const.OPCAO_LISTARCC, contas);
                break;
            case "2":
                servico.listarContas(Const.OPCAO_LISTARCP, contas);
                break;
            case "3":
                servico.listarContas(Const.OPCAO_LISTARCI, contas);
                break;
            default:
                menuRelatorios();
        }
        inicio();
    }

    public void menuCriarConta() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Escolha o tipo de conta\n")
                .append("1 - Conta Corrente\n")
                .append("2 - Conta Poupança\n")
                .append("3 - Conta Investimento\n")
                .append("0 - Retornar ao menu interior");
        System.out.println(retorno);

        String opcao = input.nextLine();
        if (!opcao.isBlank()) {
            switch (opcao) {
                case Const.OPCAO_CORRENTE:
                    menuContaCorrente();
                    break;
                case Const.OPCAO_POUPANCA:
                    menuContaPoupanca();
                    break;
                case Const.OPCAO_INVESTIMENTO:
                    menuContaInvestimento();
                    break;
            }
        }
    }

    public void menuContaCorrente() {
        Conta conta = menuConta(null);
        ContaCorrente cc = new ContaCorrente(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(), conta.getAgencia());
        servico.salvarConta(cc, contas);
        System.out.println("Conta Corrente: " + cc.getConta() + " criada!");
        inicio();
    }

    public void menuContaPoupanca() {
        Conta conta = menuConta(null);
        System.out.println("Rendimento Anual: ");
        String rendimentoAnual = input.nextLine();

        ContaPoupanca cp = Valida.validaContaPoupanca(conta, rendimentoAnual);
        servico.salvarConta(cp, contas);
        System.out.println("Conta Poupança: " + cp.getConta() + " criada!");
        inicio();
    }

    public void menuContaInvestimento() {
        Conta conta = menuConta(null);

        boolean repete = true;
        int investimento = 0;
        ContaInvestimento ci = null;

        while (repete) {
            System.out.println("Selecione o tipo de investimento\n" +
                    "1 - Tesouro Direto - Rendimento: 6,5% \n" +
                    "2 - CDB -  Rendimento: 9,0%\n" +
                    "3 - Fundo DI - Rendimento: 11,2%");

            String opcao = input.nextLine();
            if ("1".equalsIgnoreCase(opcao)) {
                investimento = ContaInvestimento.INVESTIMENTO_TESOURO_DIRETO;
                ci = new ContaInvestimento(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(),
                        conta.getAgencia(), investimento);
                efetuarDeposito(ci);
                ci.simulacao(investimento);
            } else if ("2".equalsIgnoreCase(opcao)) {
                investimento = ContaInvestimento.INVESTIMENTO_CDB;
                ci = new ContaInvestimento(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(),
                        conta.getAgencia(), investimento);
                efetuarDeposito(ci);
                ci.simulacao(investimento);
            } else if ("3".equalsIgnoreCase(opcao)) {
                investimento = ContaInvestimento.INVESTIMENTO_FUNDO_DI;
                ci = new ContaInvestimento(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(),
                        conta.getAgencia(), investimento);
                efetuarDeposito(ci);
                ci.simulacao(investimento);
            }

            System.out.println("Confirma investimento? (S/N)");
            repete = "N".equalsIgnoreCase(input.nextLine());
        }

        ci = new ContaInvestimento(conta.getNome(), conta.getCpf(), conta.getRendaMensal(), conta.getConta(),
                conta.getAgencia(), investimento);

        servico.salvarConta(ci, contas);
        System.out.println("Conta Investimento: " + ci.getConta() + " criada!");
        inicio();
    }

    public Conta menuConta(Conta c) {
        int codigo = 0;
        System.out.println("Informe os seguintes dados solicitados:\nNome: ");
        String nome = input.nextLine();
        String cpf = null;
        if (c == null) {
            System.out.println("CPF (somente números): ");
            cpf = input.nextLine();
            codigo = servico.gerarCodigo(contas);
        }
        System.out.println("Renda Mensal: ");
        String renda = input.nextLine();
        String agencia = selecionarAgencia();

        return Valida.validaConta(nome, cpf, renda, agencia, codigo);

    }

    public String selecionarAgencia() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("Selecione agẽncia\n")
                .append("1 - Forianópolis\n")
                .append("2 - São José");
        System.out.println(retorno);

        String agencia = input.nextLine();
        if (!agencia.isBlank()) {
            if ("1".equalsIgnoreCase(agencia)) {
                return Const.AGENCIA_FLORIANOPOLIS;
            } else {
                return Const.AGENCIA_SAO_JOSE;
            }
        } else {
            return "";
        }
    }

    public void menuAlterarConta() {
        Conta c = null;
        System.out.println("Informe a conta que você deseja alterar: ");
        String codigo = input.nextLine();
        if (!codigo.isBlank()) {
            c = servico.buscarConta(Integer.valueOf(codigo), contas);
        }
        if (codigo == null) {
            inicio();
        } else {
            Conta conta = menuConta(c);
            c.setNome(conta.getNome());
            c.setAgencia(conta.getAgencia());
            c.setRendaMensal(conta.getRendaMensal());

            servico.salvarConta(c, contas);

            System.out.println("Conta: " + c.getConta() + " alterada!");
        }

        inicio();
    }

}
