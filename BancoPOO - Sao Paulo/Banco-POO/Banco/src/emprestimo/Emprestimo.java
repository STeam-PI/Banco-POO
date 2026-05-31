package emprestimo;

public class Emprestimo {
    private double taxaJuros;
    private double limiteCredito;
    private int maxParcelas;

    public Emprestimo(double taxaJuros, double limiteCredito, int maxParcelas) {
        this.taxaJuros = taxaJuros;
        this.limiteCredito = limiteCredito;
        this.maxParcelas = maxParcelas;
    }

    public double calcularParcela(double valor, int numeroParcelas) {
        return (valor * (1 + taxaJuros / 100)) / numeroParcelas;
    }

    public double getTaxaJuros() { return taxaJuros; }
    public double getLimiteCredito() { return limiteCredito; }
    public int getMaxParcelas() { return maxParcelas; }
}
