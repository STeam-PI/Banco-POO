package conta;

import pessoa.Pessoa;

public abstract class Conta {
    private int numeroDaConta;
    private double saldo;
    private Pessoa titular;
    private int parcelasEmprestimo;
    private double valorParcelasEmprestimo;
    private String senha;
    private static int autoIncrement = 1;

    public Conta(Pessoa titular, String senha) {
        this.titular = titular;
        this.senha = senha;
        this.saldo = 0d;
        this.numeroDaConta = autoIncrement++;
        this.parcelasEmprestimo = 0;
        this.valorParcelasEmprestimo = 0d;
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

    public int getNumeroDaConta() { return numeroDaConta; }
    public double getSaldo() { return saldo; }
    public Pessoa getTitular() { return titular; }
    public int getParcelasEmprestimo() { return parcelasEmprestimo; }
    public double getValorParcelasEmprestimo() { return valorParcelasEmprestimo; }

    public void setParcelasEmprestimo(int parcelas) { this.parcelasEmprestimo = parcelas; }
    public void setValorParcelasEmprestimo(double valor) { this.valorParcelasEmprestimo = valor; }
}
