package conta;

import pessoa.Pessoa;

public class ContaCorinthians extends Conta {
    private static final double LIMITE_ESPECIAL = 500.0;

    public ContaCorinthians(Pessoa titular, String senha) {
        super(titular, senha);
    }

    // Permite saque usando limite especial de R$500 além do saldo
    public boolean sacarComLimiteEspecial(double valor) {
        if (getSaldo() + LIMITE_ESPECIAL >= valor) {
            removFundos(valor);
            System.out.printf("Saque de R$%.2f realizado com sucesso!%n", valor);
            return true;
        }
        System.out.println("Valor ultrapassa o limite especial disponível (R$" + LIMITE_ESPECIAL + ").");
        return false;
    }

    public double getLimiteEspecial() { return LIMITE_ESPECIAL; }
}
