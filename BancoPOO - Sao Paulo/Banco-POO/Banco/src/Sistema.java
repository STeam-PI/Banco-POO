import banco.*;
import conta.Conta;
import emprestimo.Emprestimo;
import pessoa.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private Pessoa usuario;
    private List<Banco> bancos;
    private List<Conta> minhasContas;
    private final Scanner scanner = new Scanner(System.in);

    public Sistema() {
        inicializarBancos();
        configurarUsuario();
    }

    private void inicializarBancos() {
        bancos = new ArrayList<>();
        bancos.add(new CorinthiansColossal());
        bancos.add(new CorinthiansMaster());
        bancos.add(new CorinthiansImenso());
        bancos.add(new CorinthiansDescomunal());
        bancos.add(new CorinthiansMegazord());
    }

    private void configurarUsuario() {
        System.out.println("=================================================");
        System.out.println("  Sistema de Múltiplos Bancos - Grupo São Paulo  ");
        System.out.println("=================================================");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine().trim();
        usuario = new Pessoa(nome, cpf);
        minhasContas = new ArrayList<>();
        System.out.println("\nBem-vindo(a), " + nome + "!\n");
    }

    public void executar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = lerInt();
            switch (opcao) {
                case 1 -> trabalhar();
                case 2 -> criarConta();
                case 3 -> realizarTransferencia();
                case 4 -> pegarEmprestimo();
                case 5 -> verStatus();
                case 0 -> System.out.println("\nAté logo, " + usuario.getNome() + "!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n=================================");
        System.out.println("         MENU PRINCIPAL          ");
        System.out.println("=================================");
        System.out.println("[1] Trabalhar          (+R$1.000,00 na conta padrão)");
        System.out.println("[2] Criar Conta");
        System.out.println("[3] Transferência");
        System.out.println("[4] Pegar Empréstimo");
        System.out.println("[5] Ver Meu Status");
        System.out.println("[0] Sair");
        System.out.print("Opção: ");
    }

    // ---- [1] Trabalhar ----

    private void trabalhar() {
        if (minhasContas.isEmpty()) {
            System.out.println("Você não possui contas ativas. Crie uma conta primeiro ([2]).");
            return;
        }
        Conta contaPadrao = minhasContas.get(0);
        contaPadrao.addFundos(1000.0);
        Banco banco = encontrarBancoDaConta(contaPadrao);
        System.out.printf("Você trabalhou! +R$1.000,00 depositados na conta padrão #%d (%s).%n",
                contaPadrao.getNumeroDaConta(), banco != null ? banco.getNome() : "?");
    }

    // ---- [2] Criar Conta ----

    private void criarConta() {
        System.out.println("\n--- Bancos Disponíveis ---");
        System.out.printf("%-4s %-28s %-12s %-12s %-10s %-8s%n",
                "Nº", "Banco", "Patrim. Min.", "Juros", "Limite", "Parcelas");
        System.out.println("-".repeat(78));
        String[][] info = {
            {"R$ 5.000",   "2,0%", "R$ 50.000",      "12x"},
            {"R$ 10.000",  "1,5%", "R$ 100.000",     "12x"},
            {"R$ 20.000",  "3,0%", "R$ 200.000",     "36x"},
            {"R$ 50.000",  "1,0%", "R$ 500.000",     "24x"},
            {"R$ 100.000", "0,5%", "R$ 5.000.000",   "60x"}
        };
        for (int i = 0; i < bancos.size(); i++) {
            System.out.printf("[%d] %-28s %-12s %-12s %-10s %-8s%n",
                    i + 1, bancos.get(i).getNome(),
                    info[i][0], info[i][1], info[i][2], info[i][3]);
        }
        System.out.print("\nEscolha o banco (0 para cancelar): ");
        int opcao = lerInt();
        if (opcao == 0) return;
        int idx = opcao - 1;
        if (idx < 0 || idx >= bancos.size()) {
            System.out.println("Banco inválido.");
            return;
        }
        Banco banco = bancos.get(idx);
        if (temContaNoBanco(banco)) {
            System.out.printf("Você já possui uma conta no %s. Apenas uma conta por banco é permitida.%n",
                    banco.getNome());
            return;
        }
        System.out.print("Defina uma senha para a conta: ");
        String senha = scanner.nextLine().trim();
        Conta nova = banco.criarConta(usuario, senha);
        minhasContas.add(nova);
        if (minhasContas.size() == 1) {
            System.out.println(">> Esta é sua conta PADRÃO. O salário ao trabalhar será depositado aqui.");
        }
    }

    // ---- [3] Transferência ----

    private void realizarTransferencia() {
        if (minhasContas.isEmpty()) {
            System.out.println("Você não possui contas ativas.");
            return;
        }
        System.out.println("\n--- Tipo de Transferência ---");
        System.out.println("[1] Entre minhas contas");
        System.out.println("[2] Para pessoa fictícia");
        System.out.println("[0] Cancelar");
        System.out.print("Opção: ");
        switch (lerInt()) {
            case 1 -> transferirEntreContas();
            case 2 -> transferirParaFicticio();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private void transferirEntreContas() {
        if (minhasContas.size() < 2) {
            System.out.println("Você precisa de pelo menos 2 contas para transferir entre elas.");
            return;
        }
        System.out.println("\nConta de ORIGEM:");
        Conta origem = selecionarConta();
        if (origem == null) return;

        System.out.println("\nConta de DESTINO:");
        Conta destino = selecionarConta();
        if (destino == null) return;

        if (origem == destino) {
            System.out.println("Conta de origem e destino devem ser diferentes.");
            return;
        }

        System.out.printf("Saldo disponível: R$%.2f%n", origem.getSaldo());
        System.out.print("Valor a transferir: R$");
        double valor = lerDouble();
        if (valor <= 0) { System.out.println("Valor inválido."); return; }

        Banco bancoOrigem = encontrarBancoDaConta(origem);
        Banco bancoDestino = encontrarBancoDaConta(destino);
        if (bancoOrigem != null && bancoDestino != null) {
            bancoOrigem.realizarTransferencia(origem, destino.getNumeroDaConta(), bancoDestino, valor);
        }
    }

    private void transferirParaFicticio() {
        System.out.println("\nSelecione a conta de origem:");
        Conta origem = selecionarConta();
        if (origem == null) return;

        System.out.printf("Saldo disponível: R$%.2f%n", origem.getSaldo());
        System.out.print("Valor a transferir: R$");
        double valor = lerDouble();
        if (valor <= 0) { System.out.println("Valor inválido."); return; }
        if (origem.getSaldo() < valor) { System.out.println("Saldo insuficiente."); return; }

        origem.removFundos(valor);
        System.out.printf("Transferência de R$%.2f realizada para pessoa fictícia.%n", valor);
    }

    // ---- [4] Pegar Empréstimo ----

    private void pegarEmprestimo() {
        if (minhasContas.isEmpty()) {
            System.out.println("Você precisa de pelo menos uma conta ativa para solicitar empréstimo.");
            return;
        }

        double patrimonioTotal = calcularPatrimonio();
        System.out.printf("%nPatrimônio total: R$%.2f%n", patrimonioTotal);
        System.out.println("\n--- Verificando aprovação (bancos onde você tem conta) ---");

        List<Integer> aprovadosIdx = new ArrayList<>();
        List<Emprestimo> emprestimosAprovados = new ArrayList<>();

        for (int i = 0; i < bancos.size(); i++) {
            Banco banco = bancos.get(i);
            if (!temContaNoBanco(banco)) continue;

            Emprestimo e = banco.solicitarEmprestimo(patrimonioTotal);
            if (e != null) {
                aprovadosIdx.add(i);
                emprestimosAprovados.add(e);
                System.out.printf("[%d] %-28s APROVADO  | Taxa: %.1f%% | Limite: R$%,.2f | Parcelas: até %dx%n",
                        aprovadosIdx.size(), banco.getNome(),
                        e.getTaxaJuros(), e.getLimiteCredito(), e.getMaxParcelas());
            } else {
                System.out.printf("[ ] %-28s NEGADO%n", banco.getNome());
            }
        }

        if (aprovadosIdx.isEmpty()) {
            System.out.println("\nNenhum banco aprovou seu empréstimo com base no patrimônio atual.");
            return;
        }

        System.out.print("\nEscolha o banco para o empréstimo (0 para cancelar): ");
        int escolha = lerInt();
        if (escolha <= 0 || escolha > aprovadosIdx.size()) return;

        Banco bancoCredor = bancos.get(aprovadosIdx.get(escolha - 1));
        Emprestimo emp = emprestimosAprovados.get(escolha - 1);

        System.out.printf("%nValor desejado (máximo R$%,.2f): R$", emp.getLimiteCredito());
        double valor = lerDouble();
        if (valor <= 0 || valor > emp.getLimiteCredito()) {
            System.out.println("Valor inválido ou acima do limite.");
            return;
        }

        System.out.printf("Número de parcelas (1 a %d): ", emp.getMaxParcelas());
        int parcelas = lerInt();
        if (parcelas <= 0 || parcelas > emp.getMaxParcelas()) {
            System.out.println("Número de parcelas inválido.");
            return;
        }

        double valorParcela = emp.calcularParcela(valor, parcelas);
        System.out.printf("%nResumo do empréstimo:%n");
        System.out.printf("  Valor:    R$%.2f%n", valor);
        System.out.printf("  Taxa:     %.1f%%%n", emp.getTaxaJuros());
        System.out.printf("  Parcelas: %dx de R$%.2f%n", parcelas, valorParcela);
        System.out.print("Confirmar? (s/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("Empréstimo cancelado.");
            return;
        }

        Conta contaBanco = getContaNoBanco(bancoCredor);
        if (contaBanco == null) return;
        contaBanco.addFundos(valor);
        contaBanco.adicionarEmprestimo(valor, emp.getTaxaJuros(), parcelas, valorParcela);
        System.out.printf("Empréstimo de R$%.2f creditado na conta #%d (%s).%n",
                valor, contaBanco.getNumeroDaConta(), bancoCredor.getNome());
    }

    // ---- [5] Ver Meu Status ----

    private void verStatus() {
        System.out.println("\n=== Seu Status ===");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF:  " + usuario.getCpf());

        if (minhasContas.isEmpty()) {
            System.out.println("Nenhuma conta ativa.");
            return;
        }

        System.out.println("\n--- Contas Ativas ---");
        double totalSaldo = 0;
        double totalEmprestado = 0;
        for (Conta c : minhasContas) {
            Banco banco = encontrarBancoDaConta(c);
            String nomeBanco = banco != null ? banco.getNome() : "Desconhecido";
            boolean isPadrao = minhasContas.indexOf(c) == 0;
            System.out.printf("Conta #%-4d | %-28s | Saldo: R$%10.2f%s%n",
                    c.getNumeroDaConta(), nomeBanco, c.getSaldo(),
                    isPadrao ? "  [CONTA PADRÃO]" : "");
            totalSaldo += c.getSaldo();

            List<Conta.EmprestimoRegistro> emps = c.getEmprestimos();
            if (!emps.isEmpty()) {
                double totalConta = 0;
                for (Conta.EmprestimoRegistro er : emps) totalConta += er.valor;
                totalEmprestado += totalConta;
                System.out.printf("   Empréstimos: %d contrato(s) | Total: R$%.2f%n",
                        emps.size(), totalConta);
                for (int j = 0; j < emps.size(); j++) {
                    Conta.EmprestimoRegistro er = emps.get(j);
                    System.out.printf("     [%d] R$%,.2f | Taxa: %.1f%% | %dx de R$%.2f/mês%n",
                            j + 1, er.valor, er.taxa, er.parcelas, er.valorParcela);
                }
            }
        }
        System.out.println("\n---------------------------------");
        System.out.printf("Patrimônio Total:    R$%,.2f%n", totalSaldo);
        if (totalEmprestado > 0) {
            System.out.printf("Total em Empréstimos: R$%,.2f%n", totalEmprestado);
        }
    }

    // ---- Utilitários ----

    private Conta selecionarConta() {
        for (int i = 0; i < minhasContas.size(); i++) {
            Conta c = minhasContas.get(i);
            Banco b = encontrarBancoDaConta(c);
            System.out.printf("[%d] Conta #%d | %-28s | Saldo: R$%.2f%n",
                    i + 1, c.getNumeroDaConta(), b != null ? b.getNome() : "?", c.getSaldo());
        }
        System.out.print("Escolha (0 para cancelar): ");
        int idx = lerInt() - 1;
        if (idx < 0 || idx >= minhasContas.size()) return null;
        return minhasContas.get(idx);
    }

    private Banco encontrarBancoDaConta(Conta conta) {
        for (Banco b : bancos) {
            if (b.temConta(conta)) return b;
        }
        return null;
    }

    private boolean temContaNoBanco(Banco banco) {
        for (Conta c : minhasContas) {
            if (banco.temConta(c)) return true;
        }
        return false;
    }

    private Conta getContaNoBanco(Banco banco) {
        for (Conta c : minhasContas) {
            if (banco.temConta(c)) return c;
        }
        return null;
    }

    private double calcularPatrimonio() {
        double total = 0;
        for (Conta c : minhasContas) total += c.getSaldo();
        return total;
    }

    private int lerInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
