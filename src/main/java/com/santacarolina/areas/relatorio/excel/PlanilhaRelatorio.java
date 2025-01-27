package com.santacarolina.areas.relatorio.excel;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.model.ProdutoDuplicata;
import com.santacarolina.util.StringConversor;

public class PlanilhaRelatorio {

    private static List<ProdutoDuplicata> listaRelatorio;
    private static Sheet sheet;
    private static Map<String, Integer> mapaColuna = new HashMap<>();
    private static Workbook workbook;
    private static int linha;

    public static void main(Workbook workbook, List<ProdutoDuplicata> listaRelatorio) {
        PlanilhaRelatorio.workbook = workbook;
        PlanilhaRelatorio.listaRelatorio = listaRelatorio;
        criaPlanilha();
    }

    private static void criaPlanilha() {
        sheet = workbook.createSheet("Relatório de Caixa");
        criaColunas();
        criaLinhasPasta();
    }

    private static void criaColunas() {
        int coluna = 1;

        Row linhaMes = sheet.createRow(0);
        Map<Integer, List<ProdutoDuplicata>> mapaPorAno = listaRelatorio.stream()
        .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getYear()));

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int ano : mapaPorAno.keySet()) {
            List<ProdutoDuplicata> listaAno = mapaPorAno.getOrDefault(ano, Collections.emptyList());
            Map<Month, List<ProdutoDuplicata>> mapaPorMes = listaAno.stream()
            .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getMonth()));

            for (Month month : mapaPorMes.keySet().stream().sorted().toList()) {
                Cell cellMes = linhaMes.createCell(coluna);
                String nomeColuna = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.of("pt", "BR")) + "  " + ano;
                cellMes.setCellValue(nomeColuna);
                cellMes.setCellStyle(style);
                mapaColuna.put(nomeColuna, coluna);
                coluna++;
            }
        }

        PropertyTemplate bordasCabecalho = new PropertyTemplate();
        bordasCabecalho.drawBorders(new CellRangeAddress(0, 0, 0, mapaColuna.keySet().size()), 
            BorderStyle.THIN, 
            BorderExtent.OUTSIDE);
        bordasCabecalho.applyBorders(sheet);

        PropertyTemplate bordasLinhas = new PropertyTemplate();
        bordasLinhas.drawBorders(new CellRangeAddress(1, linha, 0, mapaColuna.keySet().size()),
            BorderStyle.THIN, 
            BorderExtent.INSIDE_VERTICAL);
        bordasLinhas.applyBorders(sheet);

        PropertyTemplate bordaPrimeiraColuna = new PropertyTemplate();
        bordaPrimeiraColuna.drawBorders(new CellRangeAddress(1, linha, 0, 0),
            BorderStyle.THIN, 
            BorderExtent.OUTSIDE);
        bordaPrimeiraColuna.applyBorders(sheet);
    }

    private static void criaLinhasPasta() {
        Map<String, List<ProdutoDuplicata>> mapaPorPasta = listaRelatorio.stream()
            .collect(Collectors.groupingBy(prod -> prod.getDuplicata().getDocumento().getPasta().getNome()));

        linha = 1;

        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);

        for (String pasta : mapaPorPasta.keySet()) {
            List<ProdutoDuplicata> listaPasta = mapaPorPasta.getOrDefault(pasta, Collections.emptyList());
            Row linhaPasta = sheet.createRow(linha);
            Cell cellPasta = linhaPasta.createCell(0);
            cellPasta.setCellValue(pasta);
            cellPasta.setCellStyle(style);

            for (int coluna = 1; coluna > mapaColuna.size(); coluna++) {
                Cell celulaPintada = linhaPasta.createCell(coluna);
                celulaPintada.setCellStyle(style);
            }

            linha++;
            criaLinhasFluxo(listaPasta);
        }

        for (int coluna = 0; coluna > mapaColuna.keySet().size(); coluna++) sheet.autoSizeColumn(coluna);

    }

    private static void criaLinhasFluxo(List<ProdutoDuplicata> listaPasta) {
        Map<FluxoCaixa, List<ProdutoDuplicata>> mapaPorFluxo = listaPasta.stream()
            .collect(Collectors.groupingBy(prod -> prod.getProduto().getClassificacao().getCategoria().getFluxoCaixa()));

        List<FluxoCaixa> setFluxo = mapaPorFluxo.keySet().stream()
            .sorted(Comparator.comparing(fluxo -> fluxo.getValue()))
            .toList();
        
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (FluxoCaixa fluxo : setFluxo) {
            List<ProdutoDuplicata> listaFluxo = mapaPorFluxo.getOrDefault(fluxo, Collections.emptyList());
            String nomeFluxo = fluxo == FluxoCaixa.DESPESA ? "DESPESA" : "RECEITA";
            Row linhaFluxo = sheet.createRow(linha);
            Cell cellFluxo = linhaFluxo.createCell(0);
            cellFluxo.setCellValue(StringUtils.leftPad(nomeFluxo, nomeFluxo.length() + 8));
            linha++;
            criarLinhasClassificacao(listaFluxo);
       }

     }

    private static void criarLinhasClassificacao(List<ProdutoDuplicata> listaFluxo) {
        Map<String, List<ProdutoDuplicata>> mapaPorClassificacao = listaFluxo.stream()
        .collect(Collectors.groupingBy(prod -> prod.getProduto().getClassificacao().getNomeClassificacao()));

        for (String classificacao : mapaPorClassificacao.keySet()) {
            Row linhaClassificacao = sheet.createRow(linha);
            Cell cellClassificacao = linhaClassificacao.createCell(0);
            cellClassificacao.setCellValue(StringUtils.leftPad(classificacao, classificacao.length() + 16));
            List<ProdutoDuplicata> listaClassificacao = mapaPorClassificacao.getOrDefault(classificacao, Collections.emptyList());
            preencheValores(listaClassificacao, linhaClassificacao);
            linha++;
        }
     }

    private static void preencheValores(List<ProdutoDuplicata> listaClassificacao, Row linhaClassificacao) {
        Map<Integer,List<ProdutoDuplicata>> mapaAno = listaClassificacao.stream()
        .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getYear()));

        Font receitaFont = workbook.createFont();
        receitaFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());

        Font despesaFont = workbook.createFont();
        despesaFont.setColor(IndexedColors.RED.getIndex());

        CellStyle commonStyle = workbook.createCellStyle();
        commonStyle.setAlignment(HorizontalAlignment.LEFT);

        CellStyle receitaStyle = workbook.createCellStyle();
        receitaStyle.cloneStyleFrom(commonStyle);
        receitaStyle.setFont(receitaFont);

        CellStyle despesaStyle = workbook.createCellStyle();
        despesaStyle.cloneStyleFrom(commonStyle);
        despesaStyle.setFont(despesaFont);

        for (int ano : mapaAno.keySet()) {
            List<ProdutoDuplicata> listaAno = mapaAno.getOrDefault(ano, Collections.emptyList());
            Map<Month, List<ProdutoDuplicata>> mapaPorMes = listaAno.stream()
                .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getMonth()));

            for (Month mes : mapaPorMes.keySet()) {
                List<ProdutoDuplicata> listaMes = mapaPorMes.getOrDefault(mes, Collections.emptyList());
                double somaMes = listaMes.stream()
                    .mapToDouble(prodDup -> prodDup.getProduto().getValorTotal())
                    .sum();

                String nomeColuna = mes.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.of("pt", "BR")) + "  " + ano;
                int colunaClassificacao = mapaColuna.get(nomeColuna);
                Cell cellValor = linhaClassificacao.createCell(colunaClassificacao);
                cellValor.setCellValue(StringConversor.getCurrency(somaMes));

                if (listaMes.get(0).getProduto().getClassificacao().getCategoria().getFluxoCaixa() == FluxoCaixa.DESPESA) {
                    cellValor.setCellStyle(despesaStyle);
                } else {
                    cellValor.setCellStyle(receitaStyle);
                }

            }
        }
    }

}
