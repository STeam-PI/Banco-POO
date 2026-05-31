package conta;

import pessoa.Pessoa;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private int numeroDaConta;
    private double saldo;
    private Pessoa titular;
    private String senha;
    private static int autoIncrement = 1;
    private List<EmprestimoRegistro> emprestimos = new ArrayList<>();

    public static class EmprestimoRegistro {
        public final double valor;
        public final double taxa;
        public final int parcelas;
        public final double valorParcela;

        public EmprestimoRegistro(double valor, double taxa, int parcelas, double valorParcela) {
            this.valor = valor;
            this.taxa = taxa;
            this.parcelas = parcelas;
            this.valorParcela = valorParcela;
        }
    }

    public Conta(Pessoa titular, String senha) {
        this.titular = titular;
        this.senha = senha;
        this.saldo = 0d;
        this.numeroDaConta = autoIncrement++;
    }

    public void addFundos(Double valor) {
        this.saldo += valor;
    }

    public void removFundos(Double valor) {
        this.saldo -= valor;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public void adicionarEmprestimo(double valor, double taxa, int parcelas, double valorParcela) {
        emprestimos.add(new EmprestimoRegistro(valor, taxa, parcelas, valorParcela));
    }

    public List<EmprestimoRegistro> getEmprestimos() { return emprestimos; }
    public int getNumeroDaConta() { return numeroDaConta; }
    public double getSaldo() { return saldo; }
    public Pessoa getTitular() { return titular; }
}
