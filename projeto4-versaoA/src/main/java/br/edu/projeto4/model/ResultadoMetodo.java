package br.edu.projeto4.model;

import java.util.List;

/**
 * Armazena o resultado completo da execução de um método.
 * @param solucao Vetor solução [F1, F2, F3, F4].
 * @param iteracoes Número de iterações (0 para métodos diretos).
 * @param erroFinal Erro final alcançado.
 * @param tempoExecucao Tempo de execução em milissegundos.
 * @param historicoIteracoes Lista de cada passo do método iterativo.
 * @param convergiu Indica se o método convergiu.
 * @param mensagemStatus Mensagem de status para a interface.
 */
public record ResultadoMetodo(
    double[] solucao,
    int iteracoes,
    double erroFinal,
    long tempoExecucao,
    List<IteracaoSistemaLinear> historicoIteracoes,
    boolean convergiu,
    String mensagemStatus
) {}