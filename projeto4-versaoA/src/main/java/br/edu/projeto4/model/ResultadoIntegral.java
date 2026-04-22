package br.edu.projeto4.model;

/**
 * Resultado da integração numérica.
 */
public class ResultadoIntegral {
    private final double valor;
    private final double erro;
    private final long tempoExecucao;
    private final int n;

    public ResultadoIntegral(double valor, double erro, long tempoExecucao, int n) {
        this.valor = valor;
        this.erro = erro;
        this.tempoExecucao = tempoExecucao;
        this.n = n;
    }

    public double getValor() {
        return valor;
    }

    public double getErro() {
        return erro;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }

    public int getN() {
        return n;
    }
}