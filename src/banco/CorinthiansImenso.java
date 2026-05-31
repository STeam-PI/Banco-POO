package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoExclusive;
import pessoa.Pessoa;

public class CorinthiansImenso extends Banco {
    private static final double PATRIMONIO_MINIMO = 20000;

    public CorinthiansImenso() {
        super("Corinthians Imenso", 0.0, 3.0);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta %s criada! Titular: %s | Número: %d%n",
                getNome(), titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    // Factory Method: aprova se patrimônio total >= R$20.000
    @Override
    public Emprestimo solicitarEmprestimo(double patrimonioTotal) {
        if (patrimonioTotal < PATRIMONIO_MINIMO) return null;
        return new EmprestimoExclusive(3.0, 200000, 36);
    }
}
