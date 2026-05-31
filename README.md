# Sistema de Múltiplos Bancos - Grupo São Paulo

**Integrantes:** Débora Santos, Gabriel Lima, Gabriel Fausto, Gabriel Santos, Marcos Boé

---

## Sobre o Projeto

Simulação de um sistema bancário em console (terminal) desenvolvido em Java SE puro, sem frameworks externos. Utiliza POO, Scanner, listas e mapas em memória.

---

## Bancos Disponíveis

| Banco | Patrimônio Mínimo | Juros | Limite | Parcelas |
|---|---|---|---|---|
| Corinthians Colossal | R$ 5.000 | 2,0% | R$ 50.000 | 12x |
| Corinthians Master | R$ 10.000 | 1,5% | R$ 100.000 | 12x |
| Corinthians Imenso | R$ 20.000 | 3,0% | R$ 200.000 | 36x |
| Corinthians Descomunal | — (sem análise de crédito) | 1,0% | R$ 500.000 | 24x |
| Corinthians Megazord | R$ 100.000 | 0,5% | R$ 5.000.000 | 60x |

---

## Funcionalidades

```
[1] Trabalhar          → +R$1.000,00 na conta padrão (primeira conta criada)
[2] Criar Conta        → Abre conta em um dos 5 bancos (uma por banco por CPF)
[3] Transferência      → Entre suas contas (com taxa externa) ou para pessoa fictícia
[4] Pegar Empréstimo   → Aprovação baseada no patrimônio total (Factory Method)
[5] Ver Meu Status     → Exibe saldos, contas ativas, patrimônio total e detalhamento de empréstimos
[0] Sair
```

### Regras de negócio

- **Conta padrão:** a primeira conta criada é marcada como padrão e é a única que recebe o salário ao trabalhar.
- **Uma conta por banco:** cada CPF pode ter no máximo uma conta em cada banco (até 5 no total).
- **Múltiplos empréstimos:** é possível contratar vários empréstimos; cada contrato é registrado individualmente e listado no status com valor, taxa, parcelas e valor da parcela mensal.
- **Corinthians Descomunal:** aprova empréstimos sem análise de crédito (patrimônio mínimo não exigido).

---

## Estrutura do Projeto

```
src/
├── Main.java                          → Ponto de entrada
├── Sistema.java                       → Menu principal e lógica de navegação
├── banco/
│   ├── Banco.java                     → Classe abstrata base (inclui Factory Method)
│   ├── CorinthiansColossal.java
│   ├── CorinthiansMaster.java
│   ├── CorinthiansImenso.java
│   ├── CorinthiansDescomunal.java
│   └── CorinthiansMegazord.java
├── conta/
│   ├── Conta.java                     → Classe abstrata base (lista de empréstimos)
│   └── ContaCorinthians.java          → Conta utilizada pelos bancos Corinthians
├── emprestimo/
│   ├── Emprestimo.java                → Juros, limite e parcelas
│   ├── EmprestimoBasico.java
│   ├── EmprestimoExclusive.java
│   └── EmprestimoPremium.java
└── pessoa/
    └── Pessoa.java
```

---

## Padrão de Projeto

**Factory Method** aplicado em `Banco.solicitarEmprestimo(double patrimonioTotal)`:  
cada banco decide, de forma independente, se aprova o empréstimo e qual oferta retorna com base no patrimônio total do cliente.

---

## Como Executar

**IntelliJ IDEA:**
1. Abra a pasta `Banco-POO`
2. Navegue até `Main.java`
3. Clique em ▶ ou pressione `Shift + F10`

**VS Code** (com Extension Pack for Java):
1. Abra a pasta `Banco-POO`
2. Navegue até `Main.java`
3. Clique em **Run** acima do método `main` ou pressione `F5`

**Terminal:**
```bash
cd src
javac -d ../out Main.java
java -cp ../out Main
```
