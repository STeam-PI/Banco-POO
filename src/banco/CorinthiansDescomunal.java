package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoPremium;
import pessoa.Pessoa;

public class CorinthiansDescomunal extends Banco {

    public CorinthiansDescomunal() {
        super("Corinthians Descomunal", 0.0, 1.0);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta %s criada! Titular: %s | Número: %d%n",
                getNome(), titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    // Factory Method: sem análise de crédito — sempre aprova
    @Override
    public Emprestimo solicitarEmprestimo(double patrimonioTotal) {
        return new EmprestimoPremium(1.0, 500000, 24);
    }
}
