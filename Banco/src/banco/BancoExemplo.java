package banco;

import conta.Conta;
import conta.ContaCorrente;
import pessoa.Pessoa;

public class BancoExemplo extends Banco {

    public BancoExemplo() {
        super("Banco Exemplo", 0.0, 2.0);
    }

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
        ContaCorrente conta = new ContaCorrente(titular, senha);
        adicionarConta(conta);
        return conta;
    }
}
