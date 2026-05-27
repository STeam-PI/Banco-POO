package banco;

import conta.Conta;
import pessoa.Pessoa;

import java.util.HashMap;
import java.util.Map;

public abstract class Banco {
    private String nome;
    private Map<Integer, Conta> contas = new HashMap<>();
    private double taxaDeTransferenciaInterna;
    private double taxaDeTransferenciaExterna;

    public Banco(String nome, double taxaInterna, double taxaExterna) {
        this.nome = nome;
        this.taxaDeTransferenciaInterna = taxaInterna;
        this.taxaDeTransferenciaExterna = taxaExterna;
    }

    public abstract Conta criarConta(Pessoa titular, String senha);

    public void adicionarConta(Conta conta) {
        contas.put(conta.getNumeroDaConta(), conta);
    }

    public boolean realizarTransferencia(Conta contaOrigem, int numeroDaConta, Banco bancoDestino, double vTransferencia) {
        Conta contaDestino = bancoDestino.buscarConta(numeroDaConta);

        if (contaDestino == null) {
            System.out.println("Não foi possivel realizar transferencia!");
            return false;
        }

        double taxa = (bancoDestino == this) ? taxaDeTransferenciaInterna : taxaDeTransferenciaExterna;
        double totalDebitado = vTransferencia * (1 + taxa / 100);

        if (contaOrigem.getSaldo() < totalDebitado) {
            System.out.println("Saldo insuficiente para realizar transferência!");
            return false;
        }

        contaOrigem.removFundos(totalDebitado);
        contaDestino.addFundos(vTransferencia);
        System.out.printf("Transferência realizada! Valor: R$%.2f | Taxa cobrada: R$%.2f%n",
                vTransferencia, totalDebitado - vTransferencia);
        return true;
    }

    public Conta buscarConta(int numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta == null) {
            System.out.println("Conta não encontrada!");
        }
        return conta;
    }

    public String getNome() { return nome; }
    public double getTaxaDeTransferenciaInterna() { return taxaDeTransferenciaInterna; }
    public double getTaxaDeTransferenciaExterna() { return taxaDeTransferenciaExterna; }
}
