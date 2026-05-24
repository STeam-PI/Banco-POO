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

    public abstract Conta criarConta(Pessoa titular, String senha);

    public boolean realizarTransferencia(Conta contaOrigem, int numeroDaConta, Banco bancoDestino, double vTransferencia) {
        Conta contaDestino = bancoDestino.buscarConta(numeroDaConta);

        if (contaDestino != null) {
            contaOrigem.removFundos(vTransferencia);
            contaDestino.addFundos(vTransferencia);
            System.out.println("Transferencia realizada com sucesso!");
        } else {
            System.out.println("Não foi possivel realizar transferencia!");
            return false;
        }

        return true;
    }

    public Conta buscarConta(int numeroConta) {
        if (contas.containsKey(numeroConta)) {
            return contas.get(numeroConta);
        } else {
            System.out.println("Conta não encontrada!");
        }
        return null;
    }
}
