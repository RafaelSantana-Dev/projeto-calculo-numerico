package br.edu.projeto4.model;

/**
 * Representa os dados de uma única iteração de um método iterativo.
 * Usado para popular a tabela e o gráfico.
 * O 'record' gera automaticamente os getters: k(), f1(), f2(), etc.
 */
public record IteracaoSistemaLinear(
    int k,
    double f1,
    double f2,
    double f3,
    double f4,
    double erro
) {}