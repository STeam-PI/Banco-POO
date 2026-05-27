package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoBasico;
import emprestimo.EmprestimoExclusive;
import emprestimo.EmprestimoPremium;
import pessoa.Pessoa;

public class CorinthiansMaster extends Banco {

    public CorinthiansMaster() {
        super("Corinthians Master", 0.0, 1.5);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta Corinthians Master criada! Titular: %s | Número: %d%n",
                titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    // Factory Method: decide o tipo de empréstimo baseado no patrimônio (saldo) do cliente
    public Emprestimo solicitarEmprestimo(Conta conta) {
        double saldo = conta.getSaldo();

        if (saldo >= 100000) {
            System.out.println("[Corinthians Master] Empréstimo PREMIUM aprovado! Taxa: 1% | Limite: R$1.000.000,00");
            return new EmprestimoPremium();
        } else if (saldo >= 10000) {
            System.out.println("[Corinthians Master] Empréstimo EXCLUSIVE aprovado! Taxa: 2% | Limite: R$100.000,00");
            return new EmprestimoExclusive();
        } else {
            System.out.println("[Corinthians Master] Empréstimo BÁSICO aprovado! Taxa: 5% | Limite: R$10.000,00");
            return new EmprestimoBasico();
        }
    }
}
