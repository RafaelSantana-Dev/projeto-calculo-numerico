package br.edu.projeto4.controller;

import br.edu.projeto4.model.IteracaoSistemaLinear;
import br.edu.projeto4.model.ResultadoMetodo;
import br.edu.projeto4.model.SistemaLinear;
import br.edu.projeto4.service.MetodoGauss;
import br.edu.projeto4.service.MetodoGaussSeidel;
// Remova todos os imports de classes que foram excluídas (Bissecao, Newton, etc.)

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class MainController {

    // --- Seus @FXML fields ---
    @FXML private TextArea sistemaTextArea;
    @FXML private TextField f1Field, f2Field, f3Field, f4Field;
    @FXML private TextField iteracoesField, erroFinalField, tempoField;
    @FXML private TextField tolField;
    @FXML private Label statusLabel;
    @FXML private VBox resultsVBox;
    @FXML private TableView<IteracaoSistemaLinear> iteracoesTable;
    @FXML private TableColumn<IteracaoSistemaLinear, Integer> colK;
    @FXML private TableColumn<IteracaoSistemaLinear, Double> colF1, colF2, colF3, colF4, colErro;
    @FXML private LineChart<Number, Number> convergenciaChart;
    @FXML private NumberAxis xAxis, yAxis;

    private SistemaLinear sistema;

    @FXML
    public void initialize() {
        double[][] A = {
            {4, -1, 0, 0},
            {-1, 4, -1, 0},
            {0, -1, 4, -1},
            {0, 0, -1, 3}
        };
        double[] b = {50, 0, 80, 0};
        this.sistema = new SistemaLinear(A, b);

        sistemaTextArea.setText(formatarSistema(A, b));
        configurarTabela();
        resultsVBox.setVisible(false);
    }

    private void configurarTabela() {
        colK.setCellValueFactory(new PropertyValueFactory<>("k"));
        colF1.setCellValueFactory(new PropertyValueFactory<>("f1"));
        colF2.setCellValueFactory(new PropertyValueFactory<>("f2"));
        colF3.setCellValueFactory(new PropertyValueFactory<>("f3"));
        colF4.setCellValueFactory(new PropertyValueFactory<>("f4"));
        colErro.setCellValueFactory(new PropertyValueFactory<>("erro"));
    }

    @FXML
    private void resolverPorGauss() {
        limparResultadosAnteriores();
        // CORREÇÃO: Instanciando a classe diretamente e chamando o método
        MetodoGauss metodoGauss = new MetodoGauss();
        ResultadoMetodo resultado = metodoGauss.resolver(sistema);
        exibirResultadoFinal(resultado, "Eliminação de Gauss");
        iteracoesTable.setVisible(false);
        convergenciaChart.setVisible(false);
    }

    @FXML
    private void resolverPorGaussSeidel() {
        limparResultadosAnteriores();
        try {
            double tolerancia = Double.parseDouble(tolField.getText());
            int maxIteracoes = 1000; // Aumentar se necessário

            if (!MetodoGaussSeidel.checarConvergencia(sistema)) {
                statusLabel.setText("Aviso: Convergência não garantida pelo critério das linhas.");
                statusLabel.setStyle("-fx-text-fill: orange;");
            }

            // CORREÇÃO: Removida a camada "Sessao", chamando o método estático diretamente
            ResultadoMetodo resultado = MetodoGaussSeidel.resolver(sistema, tolerancia, maxIteracoes);

            exibirResultadoFinal(resultado, "Gauss-Seidel");

            if (resultado.historicoIteracoes() != null) {
                iteracoesTable.setItems(FXCollections.observableArrayList(resultado.historicoIteracoes()));
                plotarGrafico(resultado);
                iteracoesTable.setVisible(true);
                convergenciaChart.setVisible(true);
            }
             if (!resultado.convergiu()) {
                statusLabel.setText("Método não convergiu no número máximo de iterações!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: Tolerância inválida!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void resolverPorJacobi() {
        statusLabel.setText("Método de Jacobi não implementado nesta correção. Foco em Gauss e Gauss-Seidel.");
        statusLabel.setStyle("-fx-text-fill: blue;");
    }

    private void exibirResultadoFinal(ResultadoMetodo resultado, String metodoUsado) {
        resultsVBox.setVisible(true);
        double[] solucao = resultado.solucao();

        f1Field.setText(String.format("%.8f", solucao[0]));
        f2Field.setText(String.format("%.8f", solucao[1]));
        f3Field.setText(String.format("%.8f", solucao[2]));
        f4Field.setText(String.format("%.8f", solucao[3]));

        iteracoesField.setText(String.valueOf(resultado.iteracoes()));
        erroFinalField.setText(String.format("%.2e", resultado.erroFinal()));
        tempoField.setText(resultado.tempoExecucao() + " ms");

        String statusText = metodoUsado + ": " + resultado.mensagemStatus();
        statusLabel.setText(statusText);
        statusLabel.setStyle(resultado.convergiu() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    private void plotarGrafico(ResultadoMetodo resultado) {
        convergenciaChart.getData().clear();
        xAxis.setLabel("Iteração (k)");
        yAxis.setLabel("Erro Relativo (log)");
        convergenciaChart.setTitle("Convergência do Método");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Erro vs. Iteração");

        for (IteracaoSistemaLinear it : resultado.historicoIteracoes()) {
            // CORREÇÃO: Acessando o campo 'k' do record, não 'getIteracao()'
            // Ignoramos k=0 para o gráfico não ter erro infinito
            if (it.k() > 0) {
                 // Usando escala logarítmica para melhor visualização
                series.getData().add(new XYChart.Data<>(it.k(), Math.log10(it.erro())));
            }
        }
        convergenciaChart.getData().add(series);
    }

    private void limparResultadosAnteriores() {
        resultsVBox.setVisible(false);
        statusLabel.setText("Aguardando comando...");
        statusLabel.setStyle("-fx-text-fill: black;");
        iteracoesTable.getItems().clear();
        convergenciaChart.getData().clear();
        Arrays.asList(f1Field, f2Field, f3Field, f4Field, iteracoesField, erroFinalField, tempoField)
              .forEach(TextField::clear);
    }

    private String formatarSistema(double[][] A, double[] b) {
        // Seu método de formatação parece ok, mantido como está
        StringBuilder sb = new StringBuilder();
        String[] vars = {"F1", "F2", "F3", "F4"};
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] != 0) {
                    sb.append(String.format("%+.2f%s ", A[i][j], vars[j]));
                }
            }
            sb.append(String.format("= %.2f\n", b[i]));
        }
        return sb.toString().replace("+1.00", "+ ").replace("-1.00", "- ");
    }
}