package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoPremium;
import pessoa.Pessoa;

public class CorinthiansMegazord extends Banco {
    private static final double PATRIMONIO_MINIMO = 100000;

    public CorinthiansMegazord() {
        super("Corinthians Megazord", 0.0, 0.5);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta %s criada! Titular: %s | Número: %d%n",
                getNome(), titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    // Factory Method: aprova se patrimônio total >= R$100.000
    @Override
    public Emprestimo solicitarEmprestimo(double patrimonioTotal) {
        if (patrimonioTotal < PATRIMONIO_MINIMO) return null;
        return new EmprestimoPremium(0.5, 5000000, 60);
    }
}
