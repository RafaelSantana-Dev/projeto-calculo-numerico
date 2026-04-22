package br.edu.projeto4.service;

public class VerificadorConvergencia {
    public static boolean isDiagonalDominante(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            double diag = Math.abs(a[i][i]);
            double soma = 0;
            for (int j = 0; j < a.length; j++) {
                if (i != j) soma += Math.abs(a[i][j]);
            }
            if (diag <= soma) return false;
        }
        return true;
    }
}