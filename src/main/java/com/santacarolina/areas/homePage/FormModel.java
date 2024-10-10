package com.santacarolina.areas.homePage;

import com.santacarolina.areas.homePage.graphData.ExpenseCategory;
import com.santacarolina.areas.homePage.graphData.ExpenseIncomeSerie;
import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ProdutoDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.ClassificacaoContabil;
import org.knowm.xchart.CategorySeries;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FormModel {

    private static final ClassificacaoDAO classificacaoDAO = new ClassificacaoDAO();
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();
    private static final DuplicataDAO duplicataDAO = new DuplicataDAO();

    private List<ProdutoDTO> produtos;
    private List<ClassificacaoContabil> classificacaoList;
    private List<DuplicataDTO> duplicataList;

    private List<ExpenseCategory> pieSeriesList;
    private List<ExpenseIncomeSerie> expenseIncomeSeries;
    private double expenseIncomeLimit;
    private ExpenseIncomeSerie resultingLine;

    public FormModel() throws FetchFailException {
        produtos = produtoDAO.findAll().stream()
                .filter(p -> p.getDataEmissao().getYear() == 2024)
                .collect(Collectors.toList());
        classificacaoList = classificacaoDAO.getAll().stream()
                .filter(c -> c.getFluxoCaixa() == FluxoCaixa.DESPESA)
                .filter(c -> c.getId() != 105 && c.getId() != 117)
                .collect(Collectors.toList());
        duplicataList = duplicataDAO.findAll().stream()
                .filter(dto -> dto.getDataVencimento().getYear() == 2024)
                .collect(Collectors.toList());
    }

    public double getExpenseIncomeLimit() {
        return duplicataList.stream()
                .mapToDouble(DuplicataDTO::getValor)
                .max().orElse(0);
    }

    public ExpenseIncomeSerie getResultingLine() { return buildExpenseIncomeSerie(duplicataList, "RESULTADOS"); }

    public List<ExpenseCategory> getPieSeriesList() {
        if (pieSeriesList == null) {
            List<ExpenseCategory> expenseCategoryList = new ArrayList<>();
            for (ClassificacaoContabil classificacao : classificacaoList) {
                double valorClassificacao = produtos.stream()
                        .filter(p -> p.getClassificacaoId() == classificacao.getId())
                        .mapToDouble(dto -> dto.getValorUnit()*dto.getQuantidade())
                        .sum();
                ExpenseCategory ec = new ExpenseCategory(classificacao.getNomeClassificacao(), valorClassificacao);
                expenseCategoryList.add(ec);
            }
            List<ExpenseCategory> biggestExpenses = expenseCategoryList.stream()
                    .sorted(Comparator.comparingDouble(ExpenseCategory::getValor))
                    .limit(5)
                    .toList();
            expenseCategoryList.removeAll(biggestExpenses);
            double otherValues = expenseCategoryList.stream()
                    .mapToDouble(ExpenseCategory::getValor)
                    .sum();
            ExpenseCategory others = new ExpenseCategory("OUTROS", otherValues);
            pieSeriesList = new ArrayList<>();
            pieSeriesList.addAll(biggestExpenses);
            pieSeriesList.add(others);
        }
        return pieSeriesList;
    }

    public List<ExpenseIncomeSerie> getExpenseIncomeSeries() {
        if (expenseIncomeSeries == null) {
            expenseIncomeSeries = new ArrayList<>();
            List<DuplicataDTO> incomeList = duplicataList.stream()
                    .filter(d -> d.getFluxoCaixa() == FluxoCaixa.RECEITA)
                    .toList();
            ExpenseIncomeSerie incomeSerie = buildExpenseIncomeSerie(incomeList, "RECEITA");
            List<DuplicataDTO> expenseList = duplicataList.stream()
                    .filter(d -> d.getFluxoCaixa() == FluxoCaixa.DESPESA)
                    .collect(Collectors.toList());
            expenseList.forEach(d -> d.setValor(Math.abs(d.getValor())));
            ExpenseIncomeSerie expenseSerie = buildExpenseIncomeSerie(expenseList, "DESPESA");
            expenseIncomeSeries.add(incomeSerie);
            expenseIncomeSeries.add(expenseSerie);
        }
        return expenseIncomeSeries;
    }

    private ExpenseIncomeSerie buildExpenseIncomeSerie(List<DuplicataDTO> rawList, String name) {
        List<Integer> yearList = duplicataList.stream()
                .map(d -> d.getDataVencimento().getYear())
                .sorted()
                .distinct()
                .toList();
        List<Date> listDate = new ArrayList<>();
        List<Double> listValues = new ArrayList<>();

        for (Integer year : yearList) {
            List<Month> monthRange = duplicataList.stream()
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
                        .mapToDouble(DuplicataDTO::getValor)
                        .sum();
                listValues.add(monthValues);
            }
        }
        return new ExpenseIncomeSerie(name, listDate, listValues);
    }

}
