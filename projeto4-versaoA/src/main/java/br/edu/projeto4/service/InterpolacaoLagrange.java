package br.edu.projeto4.service;

public class InterpolacaoLagrange {
    public double interpolar(double[] x, double[] y, double xAlvo) {
        double resultado = 0;
        for (int i = 0; i < x.length; i++) {
            double termo = y[i];
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    termo = termo * (xAlvo - x[j]) / (x[i] - x[j]);
                }
            }
            resultado += termo;
        }
        return resultado;
    }
}