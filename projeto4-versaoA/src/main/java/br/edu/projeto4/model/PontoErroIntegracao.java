package br.edu.projeto4.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Representa um ponto no estudo de convergência da integração.
 */
public class PontoErroIntegracao {
    private final SimpleIntegerProperty n;
    private final SimpleDoubleProperty erroTrapezios;
    private final SimpleDoubleProperty erroSimpson;

    public PontoErroIntegracao(int n, double erroTrapezios, double erroSimpson) {
        this.n = new SimpleIntegerProperty(n);
        this.erroTrapezios = new SimpleDoubleProperty(erroTrapezios);
        this.erroSimpson = new SimpleDoubleProperty(erroSimpson);
    }

    public int getN() {
        return n.get();
    }

    public SimpleIntegerProperty nProperty() {
        return n;
    }

    public double getErroTrapezios() {
        return erroTrapezios.get();
    }

    public SimpleDoubleProperty erroTrapeziosProperty() {
        return erroTrapezios;
    }

    public double getErroSimpson() {
        return erroSimpson.get();
    }

    public SimpleDoubleProperty erroSimpsonProperty() {
        return erroSimpson;
    }
}