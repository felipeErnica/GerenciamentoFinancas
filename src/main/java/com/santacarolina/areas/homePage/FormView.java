package com.santacarolina.areas.homePage;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.util.MenuDecorator;
import com.santacarolina.util.StringConversor;
import net.miginfocom.swing.MigLayout;
import org.knowm.xchart.*;
import org.knowm.xchart.style.AxesChartStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;

public class FormView {

    private static final Color GRAPH_FOREGROUND = UIManager.getColor("Graph.foreground");

    private static final Color[] PIE_CHART_COLORS = new Color[] {UIManager.getColor("PieChart.color1"),
            UIManager.getColor("PieChart.color2"), UIManager.getColor("PieChart.color3"), UIManager.getColor("PieChart.color4"),
            UIManager.getColor("PieChart.color5"), UIManager.getColor("PieChart.color6")};

    private static final Color INCOME_BLUE = UIManager.getColor("Graph.incomeColor");
    private static final Color EXPENSE_COLOR = UIManager.getColor("Graph.expenseColor");

    private JPanel mainPanel;
    private PieChart expenseCategoryChart;
    private CategoryChart expenseIncomeChart;
    private XYChart resultsChart;

    public FormView() { init(); }

    private void init() {
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
        XChartPanel<PieChart> expenseCategoryPanel = new XChartPanel<>(expenseCategoryChart);

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
                .setSeriesColors(new Color[]{INCOME_BLUE, EXPENSE_COLOR});
        MenuDecorator.paintChart(expenseIncomeChart);
        XChartPanel<CategoryChart> expenseIncomePanel = new XChartPanel<>(expenseIncomeChart);

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
                .setSeriesColors(new Color[]{Color.ORANGE});
        MenuDecorator.paintChart(resultsChart);
        XChartPanel<XYChart> resultsPanel = new XChartPanel<>(resultsChart);
        mainPanel = new JPanel(new MigLayout("insets 20",
                "[fill, shrink 50][grow, fill]",
                ""));
        mainPanel.add(expenseCategoryPanel);
        mainPanel.add(resultsPanel, "wrap");
        mainPanel.add(expenseIncomePanel, "span");
    }

    public JPanel getMainPanel() { return mainPanel; }
    public PieChart getExpenseCategoryChart() { return expenseCategoryChart; }
    public CategoryChart getExpenseIncomeChart() { return expenseIncomeChart; }
    public XYChart getResultsChart() { return resultsChart; }

}
