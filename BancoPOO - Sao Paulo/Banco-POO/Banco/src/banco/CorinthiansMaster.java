package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoExclusive;
import pessoa.Pessoa;

public class CorinthiansMaster extends Banco {
    private static final double PATRIMONIO_MINIMO = 10000;

    public CorinthiansMaster() {
        super("Corinthians Master", 0.0, 1.5);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta %s criada! Titular: %s | Número: %d%n",
                getNome(), titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    // Factory Method: aprova se patrimônio total >= R$10.000
    @Override
    public Emprestimo solicitarEmprestimo(double patrimonioTotal) {
        if (patrimonioTotal < PATRIMONIO_MINIMO) return null;
        return new EmprestimoExclusive(1.5, 100000, 12);
    }
}
