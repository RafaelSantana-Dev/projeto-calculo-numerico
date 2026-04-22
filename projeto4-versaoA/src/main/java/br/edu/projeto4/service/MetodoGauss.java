package br.edu.projeto4.service;

import br.edu.projeto4.model.IteracaoSistemaLinear;
import br.edu.projeto4.model.ResultadoMetodo;
import br.edu.projeto4.model.SistemaLinear;
import java.util.Collections;
import java.util.List;

public class MetodoGauss {

    public ResultadoMetodo resolver(SistemaLinear sistema) {
        long startTime = System.nanoTime();
        double[][] a = sistema.getA();
        double[] b = sistema.getB().clone();
        int n = b.length;

        for (int k = 0; k < n - 1; k++) {
            int pivo = k;
            for (int i = k + 1; i < n; i++) if (Math.abs(a[i][k]) > Math.abs(a[pivo][k])) pivo = i;
            if (pivo != k) {
                double[] tempA = a[k]; a[k] = a[pivo]; a[pivo] = tempA;
                double tempB = b[k]; b[k] = b[pivo]; b[pivo] = tempB;
            }
            for (int i = k + 1; i < n; i++) {
                double m = a[i][k] / a[k][k];
                for (int j = k; j < n; j++) a[i][j] -= m * a[k][j];
                b[i] -= m * b[k];
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double soma = 0;
            for (int j = i + 1; j < n; j++) soma += a[i][j] * x[j];
            x[i] = (b[i] - soma) / a[i][i];
        }
        long endTime = System.nanoTime();
        long tempoExecucao = (endTime - startTime) / 1_000_000; // convert to milliseconds
        List<IteracaoSistemaLinear> historico = Collections.emptyList();
        boolean convergiu = true;
        String mensagemStatus = "Convergiu com sucesso.";
        return new ResultadoMetodo(x, 0, 0.0, tempoExecucao, historico, convergiu, mensagemStatus);
    }
}