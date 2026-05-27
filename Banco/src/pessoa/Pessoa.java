package pessoa;

import java.util.Date;

public class Pessoa {
    private String nome;
    private String cpf;
    private Date dtNascimento;

    public Pessoa(String nome, String cpf, Date dtNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dtNascimento = dtNascimento;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Date getDtNascimento() { return dtNascimento; }
}
