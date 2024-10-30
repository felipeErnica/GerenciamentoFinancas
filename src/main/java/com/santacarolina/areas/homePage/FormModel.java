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

    public static final String PASTA_CONTABIL = "pasta";
    public static final String DATA_FIM = "dataFim";
    public static final String DATA_INICIO = "dataInicio";
    public static final String DESPESAS_GRAPH = "despesas";
    public static final String RESULTADOS_GRAPH = "resultados";
    public static final String RECEITA_DESPESA_GRAPH = "receitas";
    public static final String RECEITA_DESPESA_MAX = "valorMax";

    private static PastaContabil pastaContabil;
    private static LocalDate dataInicio;
    private static LocalDate dataFim;

    private List<ClassificacaoContabil> classificacaoList;

    private List<ProdutoDuplicataDTO> unfilteredList;
    private List<ProdutoDuplicataDTO> filteredList;

    private List<ExpenseCategory> pieSeriesList;
    private List<ExpenseIncomeSerie> expenseIncomeSeries;
    private ExpenseIncomeSerie resultadosSerie;
    private double yMaxLimit;

    private boolean isUpdating;
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
        if (pastaContabil == null) mostValuablePasta(); 
        if (dataInicio == null) dataInicio = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        if (dataFim == null) dataFim = LocalDate.now();
        filteredList = unfilteredList;
    }

    private void mostValuablePasta() throws FetchFailException {
        List<PastaContabil> listPasta = new PastaDAO().findAll();
        double biggestValue = 0;
        PastaContabil selectedPasta = new PastaContabil();
        for (PastaContabil pasta : listPasta) {
            double pastaValue = unfilteredList.stream()
                .filter(dto -> dto.getPastaId() == pasta.getId())
                .mapToDouble(dto -> Math.abs(dto.getValorTotal()))
                .sum();
            if (pastaValue > biggestValue) {
                biggestValue = pastaValue;
                selectedPasta = pasta;
            }
        }
        FormModel.pastaContabil = selectedPasta;
    }

    //Métodos setters: definem as variáveis de filtro, engatilham as funções de transformação das listas para vetores nos gráficos.
    public void setPastaContabil(PastaContabil pastaContabil) { 
        if (isUpdating) return;
        isUpdating = true;
        FormModel.pastaContabil = pastaContabil; 
        filteredList = unfilteredList;
        buildGraphs();
        isUpdating = false;
    }

    public void setDataInicio(LocalDate dataInicio) {
        FormModel.dataInicio = dataInicio;
        filteredList = unfilteredList;
        buildGraphs();
        pf.firePropertyChange(DATA_INICIO, dataInicio);
    }

    public void setDataFim(LocalDate dataFim) {
        FormModel.dataFim = dataFim;
        filteredList = unfilteredList;
        buildGraphs();
        pf.firePropertyChange(DATA_FIM, dataFim);
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
        pf.firePropertyChange(RESULTADOS_GRAPH, resultadosSerie);
        pf.firePropertyChange(RECEITA_DESPESA_MAX, yMaxLimit);
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

    private void buildResultingLine() {
        filteredList.forEach(p -> {
            if (p.getFluxoCaixa() == FluxoCaixa.RECEITA) p.setValorUnit(Math.abs(p.getValorUnit()));
            else p.setValorUnit(Math.abs(p.getValorUnit())*-1);
        });
        resultadosSerie = buildExpenseIncomeSerie(filteredList, "RESULTADOS"); 
    }

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

        double incomeMax = 1000;
        if (incomeSerie != null) {
            incomeMax = incomeSerie.getValueList().stream()
                .mapToDouble(d -> d)
                .max().orElse(0);
            expenseIncomeSeries.add(incomeSerie);
        }

        //Cria a séria DESPESAS, mapeia os valores a uma data.
        List<ProdutoDuplicataDTO> expenseList = filteredList.stream()
            .filter(d -> d.getFluxoCaixa() == FluxoCaixa.DESPESA)
            .collect(Collectors.toList());
        
        expenseList.forEach(d -> d.setValorUnit(Math.abs(d.getValorUnit())));
        ExpenseIncomeSerie expenseSerie = buildExpenseIncomeSerie(expenseList, "DESPESA");

        double expenseMax = 1000;
        if (expenseSerie != null) {
            expenseMax = expenseSerie.getValueList().stream()
                .mapToDouble(d -> d)
                .max().orElse(0);
            expenseIncomeSeries.add(expenseSerie);
        }

        yMaxLimit = incomeMax >= expenseMax ? incomeMax : expenseMax;

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
        if (!listValues.isEmpty() && !listDate.isEmpty()) return new ExpenseIncomeSerie(name, listDate, listValues);
        else return null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { 
        isUpdating = true;
        pf.firePropertyChange(PASTA_CONTABIL, pastaContabil);
        pf.firePropertyChange(DATA_FIM, dataFim);
        pf.firePropertyChange(DATA_INICIO, dataInicio);
        buildGraphs(); 
        isUpdating = false;
    }

}
