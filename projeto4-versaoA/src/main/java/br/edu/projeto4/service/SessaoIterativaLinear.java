package br.edu.projeto4.service;

/**
 * Interface para sessões de métodos iterativos de sistema linear (passo a passo).
 */
public interface SessaoIterativaLinear {
    /**
     * Executa uma iteração do método.
     * @return true se houver mais iterações, false se terminou
     */
    boolean step();

    /**
     * Verifica se o método terminou (convergiu ou atingiu máximo de iterações).
     * @return true se terminou
     */
    boolean terminou();

    /**
     * Verifica se o método convergiu.
     * @return true se convergiu
     */
    boolean convergiu();

    /**
     * Obtém a iteração atual (contagem de iterações realizadas).
     * @return número da iteração atual
     */
    int getIteracaoAtual();

    /**
     * Obtém o vetor solução atual.
     * @return vetor solução como array double
     */
    double[] getSolucaoAtual();

    /**
     * Obtém o erro da iteração atual.
     * @return erro da iteração atual
     */
    double getErroAtual();

    /**
     * Obtém o resíduo da iteração atual.
     * @return resíduo da iteração atual
     */
    double getResiduoAtual();
}