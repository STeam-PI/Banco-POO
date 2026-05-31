package banco;

import conta.Conta;
import emprestimo.Emprestimo;
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

    // Factory Method: cada banco decide se aprova e qual oferta retorna
    public abstract Emprestimo solicitarEmprestimo(double patrimonioTotal);

    public void adicionarConta(Conta conta) {
        contas.put(conta.getNumeroDaConta(), conta);
    }

    public boolean temConta(Conta conta) {
        return contas.containsValue(conta);
    }

    public boolean realizarTransferencia(Conta contaOrigem, int numeroDaConta, Banco bancoDestino, double vTransferencia) {
        Conta contaDestino = bancoDestino.buscarConta(numeroDaConta);

        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada!");
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
        return contas.get(numeroConta);
    }

    public String getNome() { return nome; }
    public double getTaxaDeTransferenciaInterna() { return taxaDeTransferenciaInterna; }
    public double getTaxaDeTransferenciaExterna() { return taxaDeTransferenciaExterna; }
}
