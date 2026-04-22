package br.edu.projeto4.model;

/**
 * Resultado da derivação numérica.
 */
public class ResultadoDerivada {
    private final double valor;
    private final double erro;
    private final long tempoExecucao;
    private final double h;

    public ResultadoDerivada(double valor, double erro, long tempoExecucao, double h) {
        this.valor = valor;
        this.erro = erro;
        this.tempoExecucao = tempoExecucao;
        this.h = h;
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

    public double getH() {
        return h;
    }
}