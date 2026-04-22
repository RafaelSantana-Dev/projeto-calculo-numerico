package br.edu.projeto4.model;

import java.util.Arrays;

public class SistemaLinear {
    public final double[][] a;
    public final double[] b;
    public final String[] nomesVariaveis;

    public SistemaLinear(double[][] a, double[] b, String[] nomesVariaveis) {
        this.a = a;
        this.b = b;
        this.nomesVariaveis = nomesVariaveis;
    }

    public SistemaLinear(double[][] a, double[] b) {
        this.a = a;
        this.b = b;
        this.nomesVariaveis = null;
    }

    public double[][] getA() {
        double[][] copy = new double[a.length][];
        for (int i = 0; i < a.length; i++) copy[i] = Arrays.copyOf(a[i], a[i].length);
        return copy;
    }

    public double[] getB() {
        return b.clone();
    }

    public int getOrdem() {
        return b.length;
    }
}