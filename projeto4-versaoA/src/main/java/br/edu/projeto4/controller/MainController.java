package br.edu.projeto4.controller;

import br.edu.projeto4.model.IteracaoSistemaLinear;
import br.edu.projeto4.model.ResultadoMetodo;
import br.edu.projeto4.model.SistemaLinear;
import br.edu.projeto4.service.InterpolacaoLagrange;
import br.edu.projeto4.service.MetodoGauss;
import br.edu.projeto4.service.MetodoGaussSeidel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainController {

    private static final double[][] MATRIZ_A = {
        {10, -2, -1, 0},
        {-2, 12, -2, -1},
        {-1, -2, 15, -3},
        {0, -1, -3, 10}
    };

    private static final double[] VETOR_B = {150, 200, 100, 50};
    private static final double TOLERANCIA_SISTEMA = 1e-5;
    private static final double TOLERANCIA_RAIZ = 1e-7;

    @FXML private TabPane tabPane;
    @FXML private Tab tabRelatorio;
    @FXML private ComboBox<String> cbCriterioParada;
    @FXML private TextArea sistemaTextArea;
    @FXML private TextField f1Field;
    @FXML private TextField f2Field;
    @FXML private TextField f3Field;
    @FXML private TextField f4Field;
    @FXML private TextField iteracoesField;
    @FXML private TextField erroFinalField;
    @FXML private TextField tempoField;
    @FXML private TextField tolField;
    @FXML private Label statusLabel;
    @FXML private TableView<IteracaoSistemaLinear> iteracoesTable;
    @FXML private TableColumn<IteracaoSistemaLinear, Integer> colK;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colF1;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colF2;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colF3;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colF4;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colErro;
    @FXML private LineChart<Number, Number> convergenciaChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML private TableView<IteracaoRaiz> tabelaIteracoesRaiz;
    @FXML private TableColumn<IteracaoRaiz, Integer> colKRaiz;
    @FXML private TableColumn<IteracaoRaiz, Double> colARaiz;
    @FXML private TableColumn<IteracaoRaiz, Double> colBRaiz;
    @FXML private TableColumn<IteracaoRaiz, Double> colXRaiz;
    @FXML private TableColumn<IteracaoRaiz, Double> colFXRaiz;
    @FXML private TableColumn<IteracaoRaiz, Double> colErroRaiz;
    @FXML private LineChart<Number, Number> graficoErroRaiz;
    @FXML private Label lblResultadoRaiz;
    @FXML private Label lblTempoExecucaoRaiz;

    @FXML private TextField txtNTrapezios;
    @FXML private TextField txtNSimpson;
    @FXML private TextField txtHDerivada;
    @FXML private Label lblIntegralTrapezios;
    @FXML private Label lblIntegralSimpson;
    @FXML private Label lblDerivada;
    @FXML private Label lblErroTrapezios;
    @FXML private Label lblErroSimpson;
    @FXML private Label lblErroDerivada;
    @FXML private LineChart<Number, Number> graficoConvergencia;

    @FXML private Label lblVetorAB;
    @FXML private Label lblVetorAC;
    @FXML private Label lblProdutoEscalar;
    @FXML private Label lblAngulo;
    @FXML private Label lblVetorNormal;
    @FXML private Label lblAreaTriangulo;

    @FXML private Label lblResultadoLagrange;
    @FXML private Label lblEsperadoLagrange;

    // Parte 4 — novos elementos visuais
    @FXML private TableView<LinhaVetor> tabelaVetores;
    @FXML private TableColumn<LinhaVetor, String> colVetorNome;
    @FXML private TableColumn<LinhaVetor, Double> colVetorX;
    @FXML private TableColumn<LinhaVetor, Double> colVetorY;
    @FXML private TableColumn<LinhaVetor, Double> colVetorZ;
    @FXML private TableColumn<LinhaVetor, Double> colVetorNorma;
    @FXML private BarChart<String, Number> graficoVetores;
    @FXML private CategoryAxis xAxisVetores;
    @FXML private NumberAxis yAxisVetores;

    // Parte 5 — novos elementos visuais
    @FXML private TableView<LinhaInterpolacao> tabelaInterpolacao;
    @FXML private TableColumn<LinhaInterpolacao, Double> colInterpT;
    @FXML private TableColumn<LinhaInterpolacao, Double> colInterpH;
    @FXML private TableColumn<LinhaInterpolacao, Double> colInterpL;
    @FXML private TableColumn<LinhaInterpolacao, Double> colInterpContrib;
    @FXML private LineChart<Number, Number> graficoInterpolacao;
    @FXML private NumberAxis xAxisInterp;
    @FXML private NumberAxis yAxisInterp;

    @FXML private TextArea txtRelatorio;

    private final SistemaLinear sistema = new SistemaLinear(MATRIZ_A, VETOR_B);
    private ResultadoMetodo ultimoResultadoSistema;
    private List<IteracaoRaiz> ultimasIteracoesRaiz = List.of();
    private String ultimoRelatorio = "";

    @FXML
    public void initialize() {
        Locale.setDefault(Locale.US);
        sistemaTextArea.setText(formatarSistema());
        tolField.setText(formatarCientifico(TOLERANCIA_SISTEMA));
        txtNTrapezios.setText("100");
        txtNSimpson.setText("100");
        txtHDerivada.setText("0.001");

        cbCriterioParada.setItems(FXCollections.observableArrayList("Erro relativo"));
        cbCriterioParada.getSelectionModel().selectFirst();

        configurarTabelaSistemaLinear();
        configurarTabelaRaiz();
        configurarTabelaVetores();
        configurarTabelaInterpolacao();

        onResolverGauss();
        onCalcularRaiz();
        onCalcularIntegracao();
        calcularVetores();
        calcularInterpolacao();
        gerarRelatorio(false);
    }

    @FXML
    private void onResolverGauss() {
        ResultadoMetodo resultado = new MetodoGauss().resolver(sistema);
        ultimoResultadoSistema = resultado;
        exibirResultadoSistema(resultado, "Metodo exato: Eliminacao de Gauss");
        iteracoesTable.getItems().clear();
        convergenciaChart.getData().clear();
    }

    @FXML
    private void onResolverIterativo() {
        double tolerancia = lerDouble(tolField, TOLERANCIA_SISTEMA);
        ResultadoMetodo resultado = MetodoGaussSeidel.resolver(sistema, tolerancia, 1000);
        ultimoResultadoSistema = resultado;
        exibirResultadoSistema(resultado, "Metodo iterativo: Gauss-Seidel");
        iteracoesTable.setItems(FXCollections.observableArrayList(resultado.historicoIteracoes()));
        plotarConvergenciaSistema(resultado.historicoIteracoes());
    }

    @FXML
    private void onResetSistemaLinear() {
        tolField.setText(formatarCientifico(TOLERANCIA_SISTEMA));
        onResolverGauss();
    }

    @FXML
    private void onCalcularRaiz() {
        long inicio = System.nanoTime();
        ultimasIteracoesRaiz = calcularRaizPorBissecao(3.0, 5.0, TOLERANCIA_RAIZ);
        long tempo = (System.nanoTime() - inicio) / 1_000_000;

        IteracaoRaiz ultima = ultimasIteracoesRaiz.get(ultimasIteracoesRaiz.size() - 1);
        tabelaIteracoesRaiz.setItems(FXCollections.observableArrayList(ultimasIteracoesRaiz));
        lblResultadoRaiz.setText(String.format(
            "Resultado: w = %.10f, f(w) = %.3e, erro = %.3e",
            ultima.x(), ultima.fx(), ultima.erro()
        ));
        lblTempoExecucaoRaiz.setText("Tempo: " + tempo + " ms");
        plotarErroRaiz(ultimasIteracoesRaiz);
    }

    @FXML
    private void onCalcularIntegracao() {
        int nTrap = Math.max(1, lerInteiro(txtNTrapezios, 100));
        int nSimpson = Math.max(2, lerInteiro(txtNSimpson, 100));
        if (nSimpson % 2 != 0) {
            nSimpson++;
            txtNSimpson.setText(String.valueOf(nSimpson));
        }
        double hDerivada = Math.max(1e-9, lerDouble(txtHDerivada, 0.001));

        double exataIntegral = integralExata(0, 10);
        double trapezios = integrarTrapezios(0, 10, nTrap);
        double simpson = integrarSimpson(0, 10, nSimpson);
        double derivada = derivadaCentral(5, hDerivada);
        double derivadaExata = derivadaExata(5);

        lblIntegralTrapezios.setText(String.format("Trapezios: %.10f", trapezios));
        lblIntegralSimpson.setText(String.format("Simpson: %.10f", simpson));
        lblDerivada.setText(String.format("Derivada (t=5): %.10f", derivada));
        lblErroTrapezios.setText(String.format("Trapezios: %.3e", Math.abs(trapezios - exataIntegral)));
        lblErroSimpson.setText(String.format("Simpson: %.3e", Math.abs(simpson - exataIntegral)));
        lblErroDerivada.setText(String.format("Derivada: %.3e", Math.abs(derivada - derivadaExata)));
        plotarConvergenciaIntegracao();
    }

    @FXML
    private void onGerarRelatorio() {
        gerarRelatorio(true);
    }

    @FXML
    private void onSalvarRelatorio() {
        gerarRelatorio(false);
        escreverArquivo(Path.of("relatorio-projeto4-versaoA.txt"), ultimoRelatorio);
    }

    @FXML
    private void onLimparResultados() {
        f1Field.clear();
        f2Field.clear();
        f3Field.clear();
        f4Field.clear();
        iteracoesField.clear();
        erroFinalField.clear();
        tempoField.clear();
        iteracoesTable.getItems().clear();
        convergenciaChart.getData().clear();
        tabelaIteracoesRaiz.getItems().clear();
        graficoErroRaiz.getData().clear();
        graficoConvergencia.getData().clear();
        tabelaVetores.getItems().clear();
        graficoVetores.getData().clear();
        tabelaInterpolacao.getItems().clear();
        graficoInterpolacao.getData().clear();
        txtRelatorio.clear();
        statusLabel.setText("Status: resultados limpos.");
    }

    @FXML
    private void onExportarSistemaLinearCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("k,x1,x2,x3,x4,erro");
        for (IteracaoSistemaLinear it : iteracoesTable.getItems()) {
            linhas.add(String.format("%d,%.12f,%.12f,%.12f,%.12f,%.12e",
                it.k(), it.f1(), it.f2(), it.f3(), it.f4(), it.erro()));
        }
        escreverArquivo(Path.of("exports", "sistema-linear-iteracoes.csv"), String.join(System.lineSeparator(), linhas));
    }

    @FXML
    private void onExportarRaizCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("k,a,b,x,fx,erro");
        for (IteracaoRaiz it : ultimasIteracoesRaiz) {
            linhas.add(String.format("%d,%.12f,%.12f,%.12f,%.12e,%.12e",
                it.k(), it.a(), it.b(), it.x(), it.fx(), it.erro()));
        }
        escreverArquivo(Path.of("exports", "raiz-bissecao-iteracoes.csv"), String.join(System.lineSeparator(), linhas));
    }

    @FXML
    private void onExportarAmostrasCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("t,i(t)");
        for (int t = 0; t <= 10; t++) {
            linhas.add(String.format("%d,%.12f", t, corrente(t)));
        }
        escreverArquivo(Path.of("exports", "amostras-corrente.csv"), String.join(System.lineSeparator(), linhas));
    }

    @FXML
    private void onExportarVetoresCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("vetor,x,y,z,norma");
        for (LinhaVetor v : tabelaVetores.getItems()) {
            linhas.add(String.format("%s,%.12f,%.12f,%.12f,%.12f",
                v.nome(), v.x(), v.y(), v.z(), v.norma()));
        }
        escreverArquivo(Path.of("exports", "vetores-3d.csv"), String.join(System.lineSeparator(), linhas));
    }

    @FXML
    private void onExportarInterpolacaoCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("t_i,h(t_i),L_i(3),contribuicao");
        for (LinhaInterpolacao l : tabelaInterpolacao.getItems()) {
            linhas.add(String.format("%.12f,%.12f,%.12f,%.12f",
                l.ti(), l.hi(), l.li(), l.contrib()));
        }
        escreverArquivo(Path.of("exports", "interpolacao-lagrange.csv"), String.join(System.lineSeparator(), linhas));
    }

    @FXML
    private void onExportarConvergenciaCsv() {
        List<String> linhas = new ArrayList<>();
        linhas.add("n,erro_trapezios,erro_simpson");
        double exata = integralExata(0, 10);
        for (int n = 2; n <= 128; n *= 2) {
            linhas.add(String.format("%d,%.12e,%.12e",
                n,
                Math.abs(integrarTrapezios(0, 10, n) - exata),
                Math.abs(integrarSimpson(0, 10, n) - exata)));
        }
        escreverArquivo(Path.of("exports", "convergencia-integracao.csv"), String.join(System.lineSeparator(), linhas));
    }

    private void configurarTabelaSistemaLinear() {
        colK.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().k()));
        colF1.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().f1()));
        colF2.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().f2()));
        colF3.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().f3()));
        colF4.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().f4()));
        colErro.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().erro()));
    }

    private void configurarTabelaRaiz() {
        colKRaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().k()));
        colARaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().a()));
        colBRaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().b()));
        colXRaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().x()));
        colFXRaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().fx()));
        colErroRaiz.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().erro()));
    }

    private void exibirResultadoSistema(ResultadoMetodo resultado, String metodo) {
        double[] x = resultado.solucao();
        f1Field.setText(String.format("%.8f", x[0]));
        f2Field.setText(String.format("%.8f", x[1]));
        f3Field.setText(String.format("%.8f", x[2]));
        f4Field.setText(String.format("%.8f", x[3]));
        iteracoesField.setText(String.valueOf(resultado.iteracoes()));
        erroFinalField.setText(formatarCientifico(resultado.erroFinal()));
        tempoField.setText(resultado.tempoExecucao() + " ms");
        statusLabel.setText("Status: " + metodo + " - " + resultado.mensagemStatus());
    }

    private void plotarConvergenciaSistema(List<IteracaoSistemaLinear> historico) {
        convergenciaChart.getData().clear();
        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
        serie.setName("Erro relativo");
        for (IteracaoSistemaLinear it : historico) {
            if (it.k() > 0 && it.erro() > 0 && Double.isFinite(it.erro())) {
                serie.getData().add(new XYChart.Data<>(it.k(), Math.log10(it.erro())));
            }
        }
        convergenciaChart.getData().add(serie);
    }

    private void plotarErroRaiz(List<IteracaoRaiz> iteracoes) {
        graficoErroRaiz.getData().clear();
        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
        serie.setName("Erro");
        for (IteracaoRaiz it : iteracoes) {
            serie.getData().add(new XYChart.Data<>(it.k(), it.erro()));
        }
        graficoErroRaiz.getData().add(serie);
    }

    private void plotarConvergenciaIntegracao() {
        graficoConvergencia.getData().clear();
        double exata = integralExata(0, 10);

        XYChart.Series<Number, Number> trapezios = new XYChart.Series<>();
        trapezios.setName("Trapezios");
        XYChart.Series<Number, Number> simpson = new XYChart.Series<>();
        simpson.setName("Simpson 1/3");

        for (int n = 2; n <= 128; n *= 2) {
            trapezios.getData().add(new XYChart.Data<>(n, Math.abs(integrarTrapezios(0, 10, n) - exata)));
            simpson.getData().add(new XYChart.Data<>(n, Math.abs(integrarSimpson(0, 10, n) - exata)));
        }
        graficoConvergencia.getData().add(trapezios);
        graficoConvergencia.getData().add(simpson);
    }

    private List<IteracaoRaiz> calcularRaizPorBissecao(double aInicial, double bInicial, double tolerancia) {
        double a = aInicial;
        double b = bInicial;
        double fa = polinomio(a);
        double fb = polinomio(b);
        if (fa * fb > 0) {
            throw new IllegalArgumentException("Intervalo sem troca de sinal.");
        }

        List<IteracaoRaiz> iteracoes = new ArrayList<>();
        double erro = Math.abs(b - a);
        int k = 0;
        while (erro > tolerancia) {
            double x = (a + b) / 2.0;
            double fx = polinomio(x);
            erro = Math.abs(b - a) / 2.0;
            iteracoes.add(new IteracaoRaiz(k, a, b, x, fx, erro));

            if (Math.abs(fx) <= tolerancia) {
                break;
            }
            if (fa * fx < 0) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }
            k++;
        }
        return iteracoes;
    }

    private void calcularVetores() {
        double[] a = {0, 0, 0};
        double[] b = {2, 1, 0.5};
        double[] c = {1, 3, 0.2};
        double[] ab = subtrair(b, a);
        double[] ac = subtrair(c, a);
        double[] normal = produtoVetorial(ab, ac);
        double prodEscalar = produtoEscalar(ab, ac);
        double angulo = Math.toDegrees(Math.acos(prodEscalar / (norma(ab) * norma(ac))));
        double area = norma(normal) / 2.0;

        lblVetorAB.setText("Vetor AB: " + formatarVetor(ab));
        lblVetorAC.setText("Vetor AC: " + formatarVetor(ac));
        lblProdutoEscalar.setText(String.format("Produto escalar AB·AC: %.6f", prodEscalar));
        lblAngulo.setText(String.format("Angulo entre AB e AC: %.4f graus", angulo));
        lblVetorNormal.setText("Vetor normal (AB x AC): " + formatarVetor(normal));
        lblAreaTriangulo.setText(String.format("Area do triangulo ABC: %.6f unidades²", area));

        // Tabela de componentes
        tabelaVetores.setItems(FXCollections.observableArrayList(
            new LinhaVetor("AB",     ab[0],     ab[1],     ab[2],     norma(ab)),
            new LinhaVetor("AC",     ac[0],     ac[1],     ac[2],     norma(ac)),
            new LinhaVetor("Normal", normal[0], normal[1], normal[2], norma(normal))
        ));

        // Gráfico de barras — componentes por vetor
        graficoVetores.getData().clear();
        String[] comps = {"x", "y", "z"};
        double[][] vals = {ab, ac, normal};
        String[] nomes = {"AB", "AC", "Normal (AB×AC)"};
        for (int s = 0; s < 3; s++) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(nomes[s]);
            for (int i = 0; i < 3; i++) {
                serie.getData().add(new XYChart.Data<>(comps[i], vals[s][i]));
            }
            graficoVetores.getData().add(serie);
        }
    }

    private void calcularInterpolacao() {
        double[] t = {0, 2, 4, 6};
        double[] altitude = {100, 115, 122, 118};
        double xAlvo = 3.0;
        InterpolacaoLagrange lag = new InterpolacaoLagrange();

        // Tabela com bases de Lagrange
        List<LinhaInterpolacao> linhas = new ArrayList<>();
        double soma = 0;
        for (int i = 0; i < t.length; i++) {
            double li = 1.0;
            for (int j = 0; j < t.length; j++) {
                if (i != j) li *= (xAlvo - t[j]) / (t[i] - t[j]);
            }
            double contrib = altitude[i] * li;
            soma += contrib;
            linhas.add(new LinhaInterpolacao(t[i], altitude[i], li, contrib));
        }
        tabelaInterpolacao.setItems(FXCollections.observableArrayList(linhas));

        lblResultadoLagrange.setText(String.format("altitude(t=3) = %.4f m", soma));
        lblEsperadoLagrange.setText("Interpolado pelo polinomio de Lagrange grau 3 com 4 pontos GPS.");

        // Gráfico — curva suave do polinômio
        graficoInterpolacao.getData().clear();

        XYChart.Series<Number, Number> serieCurva = new XYChart.Series<>();
        serieCurva.setName("Polinomio de Lagrange");
        for (double ti = -0.2; ti <= 6.2; ti += 0.05) {
            serieCurva.getData().add(new XYChart.Data<>(ti, lag.interpolar(t, altitude, ti)));
        }

        XYChart.Series<Number, Number> serieDados = new XYChart.Series<>();
        serieDados.setName("Dados GPS medidos");
        for (int i = 0; i < t.length; i++) {
            serieDados.getData().add(new XYChart.Data<>(t[i], altitude[i]));
        }

        XYChart.Series<Number, Number> serieEstimativa = new XYChart.Series<>();
        serieEstimativa.setName(String.format("Estimativa t=3 (%.2f m)", soma));
        serieEstimativa.getData().add(new XYChart.Data<>(xAlvo, soma));

        graficoInterpolacao.getData().addAll(serieCurva, serieDados, serieEstimativa);
    }

    private void gerarRelatorio(boolean abrirAba) {
        StringBuilder sb = new StringBuilder();
        sb.append("Projeto 4 - Versao A - Otimizacao de Sistemas Inteligentes e IA").append(System.lineSeparator());
        sb.append(System.lineSeparator()).append("Parte 1 - Sistema linear").append(System.lineSeparator());
        sb.append(formatarSistema()).append(System.lineSeparator());
        if (ultimoResultadoSistema != null) {
            sb.append(String.format("Solucao atual: x1=%s, x2=%s, x3=%s, x4=%s%n",
                f1Field.getText(), f2Field.getText(), f3Field.getText(), f4Field.getText()));
            sb.append(String.format("Iteracoes=%s, erro=%s, tempo=%s%n",
                iteracoesField.getText(), erroFinalField.getText(), tempoField.getText()));
        }

        IteracaoRaiz raiz = ultimasIteracoesRaiz.isEmpty() ? null : ultimasIteracoesRaiz.get(ultimasIteracoesRaiz.size() - 1);
        sb.append(System.lineSeparator()).append("Parte 2 - Raiz").append(System.lineSeparator());
        if (raiz != null) {
            sb.append(String.format("w=%.10f, f(w)=%.3e, erro=%.3e%n", raiz.x(), raiz.fx(), raiz.erro()));
        }

        sb.append(System.lineSeparator()).append("Parte 3 - Integracao e derivada").append(System.lineSeparator());
        sb.append(lblIntegralTrapezios.getText()).append(System.lineSeparator());
        sb.append(lblIntegralSimpson.getText()).append(System.lineSeparator());
        sb.append(lblDerivada.getText()).append(System.lineSeparator());
        sb.append(lblErroTrapezios.getText()).append(System.lineSeparator());
        sb.append(lblErroSimpson.getText()).append(System.lineSeparator());
        sb.append(lblErroDerivada.getText()).append(System.lineSeparator());

        sb.append(System.lineSeparator()).append("Parte 4 - Vetores 3D").append(System.lineSeparator());
        sb.append(lblVetorAB.getText()).append(System.lineSeparator());
        sb.append(lblVetorAC.getText()).append(System.lineSeparator());
        sb.append(lblProdutoEscalar.getText()).append(System.lineSeparator());
        sb.append(lblAngulo.getText()).append(System.lineSeparator());
        sb.append(lblVetorNormal.getText()).append(System.lineSeparator());
        sb.append(lblAreaTriangulo.getText()).append(System.lineSeparator());

        sb.append(System.lineSeparator()).append("Parte 5 - Interpolacao").append(System.lineSeparator());
        sb.append(lblResultadoLagrange.getText()).append(System.lineSeparator());

        ultimoRelatorio = sb.toString();
        txtRelatorio.setText(ultimoRelatorio);
        if (abrirAba) {
            tabPane.getSelectionModel().select(tabRelatorio);
        }
    }

    private void escreverArquivo(Path caminho, String conteudo) {
        try {
            Path parent = caminho.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(caminho, conteudo, StandardCharsets.UTF_8);
            statusLabel.setText("Status: arquivo salvo em " + caminho.toAbsolutePath());
        } catch (IOException e) {
            statusLabel.setText("Status: erro ao salvar arquivo - " + e.getMessage());
        }
    }

    private String formatarSistema() {
        return """
            10x1 - 2x2 - x3        = 150
            -2x1 + 12x2 - 2x3 - x4 = 200
            -x1 - 2x2 + 15x3 - 3x4 = 100
            -x2 - 3x3 + 10x4       = 50
            """;
    }

    private double polinomio(double w) {
        return Math.pow(w, 4) - 8 * Math.pow(w, 3) + 18 * Math.pow(w, 2) - 10 * w - 5;
    }

    private double corrente(double t) {
        return -0.1 * Math.pow(t, 4) + 2 * Math.pow(t, 3) - 8 * Math.pow(t, 2) + 15 * t + 50;
    }

    private double integralExata(double a, double b) {
        return primitivaCorrente(b) - primitivaCorrente(a);
    }

    private double primitivaCorrente(double t) {
        return -0.02 * Math.pow(t, 5) + 0.5 * Math.pow(t, 4) - (8.0 / 3.0) * Math.pow(t, 3)
            + 7.5 * Math.pow(t, 2) + 50 * t;
    }

    private double integrarTrapezios(double a, double b, int n) {
        double h = (b - a) / n;
        double soma = (corrente(a) + corrente(b)) / 2.0;
        for (int i = 1; i < n; i++) {
            soma += corrente(a + i * h);
        }
        return h * soma;
    }

    private double integrarSimpson(double a, double b, int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("Simpson 1/3 exige n par.");
        }
        double h = (b - a) / n;
        double soma = corrente(a) + corrente(b);
        for (int i = 1; i < n; i++) {
            soma += (i % 2 == 0 ? 2 : 4) * corrente(a + i * h);
        }
        return h * soma / 3.0;
    }

    private double derivadaCentral(double t, double h) {
        return (corrente(t + h) - corrente(t - h)) / (2 * h);
    }

    private double derivadaExata(double t) {
        return -0.4 * Math.pow(t, 3) + 6 * Math.pow(t, 2) - 16 * t + 15;
    }

    private double[] subtrair(double[] u, double[] v) {
        return new double[] {u[0] - v[0], u[1] - v[1], u[2] - v[2]};
    }

    private double produtoEscalar(double[] u, double[] v) {
        return u[0] * v[0] + u[1] * v[1] + u[2] * v[2];
    }

    private double[] produtoVetorial(double[] u, double[] v) {
        return new double[] {
            u[1] * v[2] - u[2] * v[1],
            u[2] * v[0] - u[0] * v[2],
            u[0] * v[1] - u[1] * v[0]
        };
    }

    private double norma(double[] u) {
        return Math.sqrt(produtoEscalar(u, u));
    }

    private String formatarVetor(double[] vetor) {
        return String.format("(%.6f, %.6f, %.6f)", vetor[0], vetor[1], vetor[2]);
    }

    private int lerInteiro(TextField campo, int padrao) {
        try {
            return Integer.parseInt(campo.getText().trim());
        } catch (RuntimeException e) {
            campo.setText(String.valueOf(padrao));
            return padrao;
        }
    }

    private double lerDouble(TextField campo, double padrao) {
        try {
            return Double.parseDouble(campo.getText().trim());
        } catch (RuntimeException e) {
            campo.setText(formatarCientifico(padrao));
            return padrao;
        }
    }

    private String formatarCientifico(double valor) {
        return String.format("%.1e", valor);
    }

    private void configurarTabelaVetores() {
        colVetorNome.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().nome()));
        colVetorX.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().x()));
        colVetorY.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().y()));
        colVetorZ.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().z()));
        colVetorNorma.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().norma()));
    }

    private void configurarTabelaInterpolacao() {
        colInterpT.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().ti()));
        colInterpH.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().hi()));
        colInterpL.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().li()));
        colInterpContrib.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().contrib()));
    }

    private record IteracaoRaiz(int k, double a, double b, double x, double fx, double erro) {}

    private record LinhaVetor(String nome, double x, double y, double z, double norma) {}

    private record LinhaInterpolacao(double ti, double hi, double li, double contrib) {}
}
