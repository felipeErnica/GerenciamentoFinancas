package com.santacarolina.areas.homePage;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.santacarolina.areas.homePage.graphData.ExpenseCategory;
import com.santacarolina.areas.homePage.graphData.ExpenseIncomeSerie;
import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.dao.ProdutoDuplicataDAO;
import com.santacarolina.dto.ProdutoDuplicataDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public static final String DESPESAS_GRAPH = "despesas";
    public static final String RESULTADOS_GRAPH = "resultados";
    public static final String RECEITA_DESPESA_GRAPH = "receitas";
    public static final String RECEITA_DESPESA_MAX = "valorMax";

    private PastaContabil pastaContabil;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private List<ClassificacaoContabil> classificacaoList;

    private List<ProdutoDuplicataDTO> unfilteredList;
    private List<ProdutoDuplicataDTO> filteredList;

    private List<ExpenseCategory> pieSeriesList;
    private List<ExpenseIncomeSerie> expenseIncomeSeries;
    private ExpenseIncomeSerie resultadosSerie;

    private PropertyFirer pf;

    public FormModel() throws FetchFailException {
        classificacaoList = new ClassificacaoDAO().findAll().stream()
            .filter(c -> c.getFluxoCaixa() == FluxoCaixa.DESPESA)
            .toList();
        unfilteredList = new ProdutoDuplicataDAO().findAll().stream()
            .filter(p -> p.getDataVencimento() != null)
            .filter(p -> p.getNomePasta() != null)
            .collect(Collectors.toList());
        pf = new PropertyFirer(this);
        updateAllData();
    }

    private void updateAllData() throws FetchFailException {
        if (pastaContabil == null) pastaContabil = new PastaDAO().findAll().get(0);
        if (dataInicio == null) dataInicio = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        if (dataFim == null) dataFim = LocalDate.now();
        filteredList = unfilteredList;
    }

    //Métodos setters: definem as variáveis de filtro, engatilham as funções de transformação das listas para vetores nos gráficos.
    public void setPastaContabil(PastaContabil pastaContabil) { 
        this.pastaContabil = pastaContabil; 
        filteredList = unfilteredList;
        buildGraphs();
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        filteredList = unfilteredList;
        buildGraphs();
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        filteredList = unfilteredList;
        buildGraphs();
    }

    //Método privado para construir os vetores de dados nos gráficos. Filtra a lista de acordo com as váriaveis definidas e retorna a lista
    //de vetores apropriada
    private void buildGraphs() {
        if (pastaContabil != null) filterPasta();
        if (dataInicio != null) filterInicio();
        if (dataFim != null) filterFim();
        buildPieSeriesList();
        buildResultingLine();
        buildExpenseIncomeGraph();
        pf.firePropertyChange(DESPESAS_GRAPH, pieSeriesList);
        pf.firePropertyChange(RECEITA_DESPESA_GRAPH, expenseIncomeSeries);
        pf.firePropertyChange(RECEITA_DESPESA_MAX, getExpenseIncomeLimit());
        pf.firePropertyChange(RESULTADOS_GRAPH, resultadosSerie);
    }

    private void filterPasta() {
        filteredList = filteredList.stream()
            .filter(p -> p.getPastaId() == pastaContabil.getId())
            .collect(Collectors.toList());
    }

    private void filterFim() {
        filteredList = filteredList.stream()
            .filter(p -> p.getDataVencimento().isBefore(dataFim))
            .collect(Collectors.toList());
    }

    private void filterInicio() {
        filteredList = filteredList.stream()
            .filter(p -> p.getDataVencimento().isAfter(dataInicio))
            .collect(Collectors.toList());
    }


    private double getExpenseIncomeLimit() {
        return filteredList.stream()
                .mapToDouble(ProdutoDuplicataDTO::getValorTotal)
                .max().orElse(0);
    }

    private void buildResultingLine() { resultadosSerie = buildExpenseIncomeSerie(filteredList, "RESULTADOS"); }

    private void  buildPieSeriesList() {
        List<ExpenseCategory> expenseCategoryList = new ArrayList<>();

        //Soma toda as entradas de uma mesma classificacao e adiciona a uma lista que mapeia a classificacao a um valor.
        for (ClassificacaoContabil classificacao : classificacaoList) {
            double valorClassificacao = filteredList.stream()
            .filter(p -> p.getClassificacaoId() == classificacao.getId())
            .mapToDouble(dto -> dto.getValorTotal())
            .sum();
            ExpenseCategory ec = new ExpenseCategory(classificacao.getNomeClassificacao(), Math.abs(valorClassificacao)*-1);
            expenseCategoryList.add(ec);
        }

        //Seleciona as 5 maiores despesas e suas classificaçoes. 
        List<ExpenseCategory> biggestExpenses = expenseCategoryList.stream()
        .sorted(Comparator.comparingDouble(ExpenseCategory::getValor))
        .filter(e -> e.getValor() != 0)
        .limit(5)
        .toList();

        pieSeriesList = new ArrayList<>();
        pieSeriesList.addAll(biggestExpenses);

        //Soma as categorias restantes em uma nova categoria 'Outros'
        if (biggestExpenses.size() == 5) {
            expenseCategoryList.removeAll(biggestExpenses);
            double otherValues = expenseCategoryList.stream()
            .mapToDouble(ExpenseCategory::getValor)
            .sum();
            ExpenseCategory others = new ExpenseCategory("OUTROS", otherValues);
            pieSeriesList.add(others);
        }

    }

    private void buildExpenseIncomeGraph() {
        expenseIncomeSeries = new ArrayList<>();

        //Cria a séria RECEITAS, mapeia os valores a uma data.
        List<ProdutoDuplicataDTO> incomeList = filteredList.stream()
        .filter(d -> d.getFluxoCaixa() == FluxoCaixa.RECEITA)
        .toList();
        ExpenseIncomeSerie incomeSerie = buildExpenseIncomeSerie(incomeList, "RECEITA");


        //Cria a séria DESPESAS, mapeia os valores a uma data.
        List<ProdutoDuplicataDTO> expenseList = filteredList.stream()
        .filter(d -> d.getFluxoCaixa() == FluxoCaixa.DESPESA)
        .collect(Collectors.toList());
        expenseList.forEach(d -> d.setValorUnit(Math.abs(d.getValorUnit())));
        ExpenseIncomeSerie expenseSerie = buildExpenseIncomeSerie(expenseList, "DESPESA");

        expenseIncomeSeries.add(incomeSerie);
        expenseIncomeSeries.add(expenseSerie);
    }

    //Método para somar entradas em uma mesmo Mês e retorna os valores mapeados
    private ExpenseIncomeSerie buildExpenseIncomeSerie(List<ProdutoDuplicataDTO> rawList, String name) {
        List<Integer> yearList = filteredList.stream()
                .map(d -> d.getDataVencimento().getYear())
                .sorted()
                .distinct()
                .toList();
        List<Date> listDate = new ArrayList<>();
        List<Double> listValues = new ArrayList<>();

        for (Integer year : yearList) {
            List<Month> monthRange = filteredList.stream()
                    .filter(d -> d.getDataVencimento().getYear() == year)
                    .map(d -> d.getDataVencimento().getMonth())
                    .sorted()
                    .distinct()
                    .toList();
            for (Month month : monthRange) {
                Date date = Date.from(LocalDate.of(year,month,1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                listDate.add(date);
                double monthValues = rawList.stream()
                        .filter(d -> d.getDataVencimento().getYear() == year)
                        .filter(d -> d.getDataVencimento().getMonth() == month)
                        .mapToDouble(ProdutoDuplicataDTO::getValorTotal)
                        .sum();
                listValues.add(monthValues);
            }
        }
        return new ExpenseIncomeSerie(name, listDate, listValues);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { 
        buildGraphs(); 
    }

}
