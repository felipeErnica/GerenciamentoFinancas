package com.santacarolina.areas.homePage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import com.santacarolina.areas.homePage.graphData.ExpenseCategory;
import com.santacarolina.areas.homePage.graphData.ExpenseIncomeSerie;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.MenuDecorator;
import com.santacarolina.util.StringConversor;

import net.miginfocom.swing.MigLayout;

public class FormView implements PropertyChangeListener {

    private final Color GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");

    private final Color[] PIE_CHART_COLORS = new Color[] {UIManager.getColor("PieChart.color1"),
            UIManager.getColor("PieChart.color2"), UIManager.getColor("PieChart.color3"), UIManager.getColor("PieChart.color4"),
            UIManager.getColor("PieChart.color5"), UIManager.getColor("PieChart.color6")};

    private final Color INCOME_BLUE = UIManager.getColor("Graph.incomeColor");
    private final Color EXPENSE_COLOR = UIManager.getColor("Graph.expenseColor");

    private JPanel mainPanel;

    private JTextField dataFimText;
    private JTextField dataInicioText;
    private JComboBox<PastaContabil> pastaComboBox;

    private XChartPanel<PieChart> expenseCategoryPanel;
    private XChartPanel<XYChart> resultsPanel;
    private XChartPanel<CategoryChart> expenseIncomePanel;

    private PieChart expenseCategoryChart;
    private CategoryChart expenseIncomeChart;
    private XYChart resultsChart;

    private List<ExpenseCategory> expenseCategorieList;
    private List<ExpenseIncomeSerie> expenseIncomeSeries;
    private ExpenseIncomeSerie resultingLine;

    public FormView() { init(); }

    private void init() {

        JLabel pastaLabel = new JLabel("Pasta Contábil");
        pastaComboBox = new JComboBox<>();
        AutoCompleteDecorator.decorate(pastaComboBox);
        pastaLabel.setLabelFor(pastaComboBox);

        JLabel inicioLabel = new JLabel("Data de Início:");
        dataInicioText = new JTextField();
        inicioLabel.setLabelFor(dataInicioText);

        JLabel fimLabel = new JLabel("Data Final:");
        dataFimText = new JTextField();
        fimLabel.setLabelFor(dataFimText);

        JPanel northPanel = new JPanel(new MigLayout("insets 20","[][grow, fill]30[][120, fill][][120, fill]"));

        northPanel.add(pastaLabel);
        northPanel.add(pastaComboBox);
        northPanel.add(inicioLabel);
        northPanel.add(dataInicioText);
        northPanel.add(fimLabel);
        northPanel.add(dataFimText);

        expenseCategoryChart = new PieChartBuilder()
            .title("Composição das Despesas")
            .theme(Styler.ChartTheme.XChart)
            .build();
        expenseCategoryChart.getStyler()
            .setLabelType(PieStyler.LabelType.NameAndPercentage)
            .setForceAllLabelsVisible(true)
            .setLabelsFontColor(GRAPH_FOREGROUND)
            .setLabelsFontColorAutomaticEnabled(false)
            .setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie)
            .setCircular(true)
            .setSeriesColors(PIE_CHART_COLORS);

        MenuDecorator.paintChart(expenseCategoryChart);
        expenseCategoryPanel = new XChartPanel<>(expenseCategoryChart);

        expenseIncomeChart = new CategoryChartBuilder()
            .title("Receitas x Despesas")
            .theme(Styler.ChartTheme.XChart)
            .xAxisTitle("Mês")
            .yAxisTitle("Valor (R$)")
            .build();
        expenseIncomeChart.getStyler()
            .setLabelsVisible(true)
            .setLabelsFontColorAutomaticEnabled(false)
            .setLabelsFontColor(GRAPH_FOREGROUND)
            .setLabelsPosition(1d)
            .setLabelsRotation(45)
            .setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar)
            .setyAxisTickLabelsFormattingFunction(StringConversor::getCurrency)
            .setYAxisTickLabelsColor(GRAPH_FOREGROUND)
            .setXAxisTickLabelsColor(GRAPH_FOREGROUND)
            .setPlotGridLinesColor(GRAPH_FOREGROUND)
            .setDatePattern("MMM-yyyy")
            .setLocale(Locale.of("pt","BR"))
            .setSeriesColors(new Color[]{INCOME_BLUE, EXPENSE_COLOR});
        MenuDecorator.paintChart(expenseIncomeChart);
        expenseIncomePanel = new XChartPanel<>(expenseIncomeChart);

        resultsChart = new XYChartBuilder()
            .title("Balanço")
            .xAxisTitle("Mês")
            .yAxisTitle("Valor (R$)")
            .theme(Styler.ChartTheme.XChart)
            .build();
        resultsChart.getStyler()
            .setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
            .setyAxisTickLabelsFormattingFunction(StringConversor::getCurrency)
            .setYAxisTickLabelsColor(GRAPH_FOREGROUND)
            .setXAxisTickLabelsColor(GRAPH_FOREGROUND)
            .setPlotGridLinesColor(GRAPH_FOREGROUND)
            .setDatePattern("MMM-yyyy")
            .setLocale(Locale.of("pt","BR"))
            .setSeriesColors(new Color[]{Color.ORANGE});
        MenuDecorator.paintChart(resultsChart);
        resultsPanel = new XChartPanel<>(resultsChart);

        JPanel centerPanel = new JPanel(new MigLayout("insets 20",
            "[fill, shrink 50][grow, fill]",
            ""));

        centerPanel.add(expenseCategoryPanel);
        centerPanel.add(resultsPanel, "wrap");
        centerPanel.add(expenseIncomePanel, "span");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() { return mainPanel; }
    public PieChart getExpenseCategoryChart() { return expenseCategoryChart; }
    public CategoryChart getExpenseIncomeChart() { return expenseIncomeChart; }
    public XYChart getResultsChart() { return resultsChart; }
    public JTextField getDataFimText() { return dataFimText; }
    public JTextField getDataInicioText() { return dataInicioText; }
    public JComboBox<PastaContabil> getPastaComboBox() { return pastaComboBox; }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FormModel.DESPESAS_GRAPH -> {
                expenseCategorieList = (List<ExpenseCategory>) evt.getNewValue();
                expenseCategoryChart.getSeriesMap().clear();

                if (!expenseCategorieList.isEmpty()) {
                    expenseCategorieList.forEach(ec -> expenseCategoryChart.addSeries(ec.getClassificacao(), ec.getValor()));
                } else {
                    PieSeries series = expenseCategoryChart.addSeries("N/a", 1);
                    series.setEnabled(false);
                }

                expenseCategoryPanel.repaint();
            }
            case FormModel.RECEITA_DESPESA_GRAPH -> {
                expenseIncomeSeries = (List<ExpenseIncomeSerie>) evt.getNewValue();
                expenseIncomeChart.getSeriesMap().clear();

                if (!expenseIncomeSeries.isEmpty()) {
                    expenseIncomeSeries.forEach(es -> expenseIncomeChart.addSeries(es.getName(),es.getDateList(),es.getValueList()));
                } else {
                    CategorySeries series = expenseIncomeChart.addSeries("A", new int[]{1}, new int[]{1});
                    series.setEnabled(false);
                }

                expenseIncomePanel.repaint();
            }
            case FormModel.RESULTADOS_GRAPH -> {
                resultingLine = (ExpenseIncomeSerie) evt.getNewValue();
                resultsChart.getSeriesMap().clear();

                if (resultingLine != null) {
                    resultsChart.addSeries(resultingLine.getName(), resultingLine.getDateList(), resultingLine.getValueList());
                } else {
                    XYSeries series = resultsChart.addSeries("a", new int[]{1});
                    series.setEnabled(false);
                }

                resultsPanel.repaint();
            }
            case FormModel.RECEITA_DESPESA_MAX -> {
                double valorMax = (double) evt.getNewValue();
                expenseIncomeChart.getStyler().setYAxisMax(valorMax*1.4d);
                expenseIncomePanel.repaint();
            }
            case FormModel.PASTA_CONTABIL -> {
                PastaContabil pastaContabil = (PastaContabil) evt.getNewValue();
                pastaComboBox.setSelectedItem(pastaContabil);
            } 
            case FormModel.DATA_INICIO -> {
                LocalDate dataInicio = (LocalDate) evt.getNewValue();
                dataInicioText.setText(dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            case FormModel.DATA_FIM -> {
                LocalDate dataFim = (LocalDate) evt.getNewValue();
                dataFimText.setText(dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

}
