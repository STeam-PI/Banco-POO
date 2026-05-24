package banco;

import conta.Conta;
import conta.ContaCorrente;
import pessoa.Pessoa;

import java.util.HashMap;
import java.util.Map;

public class BancoExemplo extends Banco {
    private Map<Double, Conta> contasExemplo = new HashMap<>();

    @Override
    public Conta criarConta(Pessoa titular, String senha) {
            return new ContaCorrente(titular, senha);
    }
}
