package br.edu.projeto4.model;

import java.util.List;

public record ResultadoSistemaLinear(double[] solucao, int iteracoes, double erroFinal, double residuoFinal, long tempoNanos, List<IteracaoSistemaLinear> historico) {
    public double[] getSolucao() { return solucao; }
    public int getIteracoes() { return iteracoes; }
    public double getErroFinal() { return erroFinal; }
    public double getResiduoFinal() { return residuoFinal; }
    public long getTempoNanos() { return tempoNanos; }
    public List<IteracaoSistemaLinear> getHistorico() { return historico; }
}