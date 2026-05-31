package banco;

import conta.Conta;
import conta.ContaCorinthians;
import emprestimo.Emprestimo;
import emprestimo.EmprestimoBasico;
import pessoa.Pessoa;

public class CorinthiansColossal extends Banco {
    private static final double PATRIMONIO_MINIMO = 5000;

    public CorinthiansColossal() {
        super("Corinthians Colossal", 0.0, 2.0);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorinthians conta = new ContaCorinthians(titular, senha);
        adicionarConta(conta);
        System.out.printf("Conta %s criada! Titular: %s | Número: %d%n",
                getNome(), titular.getNome(), conta.getNumeroDaConta());
        return conta;
    }

    @Override
    public Emprestimo solicitarEmprestimo(double patrimonioTotal) {
        if (patrimonioTotal < PATRIMONIO_MINIMO) return null;
        return new EmprestimoBasico(2.0, 50000, 12);
    }
}
