package br.edu.projeto4.service;

import br.edu.projeto4.model.IteracaoSistemaLinear;
import br.edu.projeto4.model.ResultadoMetodo;
import br.edu.projeto4.model.SistemaLinear;
import java.util.ArrayList;
import java.util.List;

public class MetodoJacobi {

    public static ResultadoMetodo resolver(SistemaLinear sistema, double tolerancia, int maxIteracoes) {
        long startTime = System.currentTimeMillis();
        List<IteracaoSistemaLinear> historico = new ArrayList<>();

        int n = sistema.getOrdem();
        double[][] A = sistema.getA();
        double[] b = sistema.getB();

        double[] x = new double[n]; // Chute inicial (vetor nulo)
        double[] xAnterior;

        int k = 0;
        double erro = Double.MAX_VALUE;

        // Adiciona o estado inicial (k=0)
        historico.add(new IteracaoSistemaLinear(k, x[0], x[1], x[2], x[3], erro));

        while (k < maxIteracoes && erro > tolerancia) {
            k++;
            xAnterior = x.clone();

            for (int i = 0; i < n; i++) {
                double soma = 0;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        soma += A[i][j] * xAnterior[j];
                    }
                }
                x[i] = (b[i] - soma) / A[i][i];
            }

            erro = calcularErroRelativo(x, xAnterior);
            // CORREÇÃO: Passando os valores individuais para o construtor do record
            historico.add(new IteracaoSistemaLinear(k, x[0], x[1], x[2], x[3], erro));
        }

        long endTime = System.currentTimeMillis();
        boolean convergiu = (erro <= tolerancia);
        String status = convergiu ? "Convergiu com sucesso." : "Não convergiu no número máximo de iterações.";

        return new ResultadoMetodo(x, k, erro, endTime - startTime, historico, convergiu, status);
    }

    private static double calcularErroRelativo(double[] xAtual, double[] xAnterior) {
        double maxDiff = 0;
        double maxVal = 0;
        for (int i = 0; i < xAtual.length; i++) {
            double diff = Math.abs(xAtual[i] - xAnterior[i]);
            if (diff > maxDiff) {
                maxDiff = diff;
            }
            if (Math.abs(xAtual[i]) > maxVal) {
                maxVal = Math.abs(xAtual[i]);
            }
        }
        return (maxVal == 0) ? maxDiff : maxDiff / maxVal;
    }
}