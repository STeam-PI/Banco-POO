package emprestimo;

public class Emprestimo {
    private double taxaJuros;
    private double valorEmprestimo;

    public Emprestimo(double taxaJuros, double valorEmprestimo) {
        this.taxaJuros = taxaJuros;
        this.valorEmprestimo = valorEmprestimo;
    }

    public double calcularParcela(int numeroParcelas) {
        return (valorEmprestimo * (1 + taxaJuros / 100)) / numeroParcelas;
    }

    public double getTaxaJuros() { return taxaJuros; }
    public double getValorEmprestimo() { return valorEmprestimo; }
}
