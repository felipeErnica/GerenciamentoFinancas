package com.santacarolina.areas.relatorio.excel;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.model.ProdutoDuplicata;

public class PlanilhaRelatorio {

    public static void criaPlanilha(Workbook workbook, List<ProdutoDuplicata> listaRelatorio) {

        Sheet sheet = workbook.createSheet("Relatório de Caixa");
        
        int coluna = 1;

        Row linhaMes = sheet.createRow(0);
        Map<Integer, List<ProdutoDuplicata>> mapaPorAno = listaRelatorio.stream()
            .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getYear()));

        Map<String, Integer> mapaColuna = new HashMap<>();

        for (int ano : mapaPorAno.keySet()) {
            List<ProdutoDuplicata> listaAno = mapaPorAno.getOrDefault(ano, Collections.emptyList());
            Map<Month, List<ProdutoDuplicata>> mapaPorMes = listaAno.stream()
                .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getMonth()));

            for (Month month : mapaPorMes.keySet()) {
                Cell cellMes = linhaMes.createCell(coluna);
                String nomeColuna = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.of("pt", "BR")) + "  " + ano;
                cellMes.setCellValue(nomeColuna);
                mapaColuna.put(nomeColuna, coluna);
                coluna++;
            }
        }

        Map<String, List<ProdutoDuplicata>> mapaPorPasta = listaRelatorio.stream()
            .collect(Collectors.groupingBy(prod -> prod.getDuplicata().getDocumento().getPasta().getNome()));

        int linha = 1;

        for (String pasta : mapaPorPasta.keySet()) {
            List<ProdutoDuplicata> listaPasta = mapaPorPasta.getOrDefault(pasta, Collections.emptyList());
            Row linhaPasta = sheet.createRow(linha);
            linha++;
            Cell cellPasta = linhaPasta.createCell(0);
            cellPasta.setCellValue(pasta);
            Map<FluxoCaixa, List<ProdutoDuplicata>> mapaPorFluxo = listaPasta.stream()
                .collect(Collectors.groupingBy(prod -> prod.getProduto().getClassificacao().getCategoria().getFluxoCaixa()));

            for (FluxoCaixa fluxo : mapaPorFluxo.keySet()) {
                List<ProdutoDuplicata> listaFluxo = mapaPorFluxo.getOrDefault(fluxo, Collections.emptyList());
                String nomeFluxo = fluxo == FluxoCaixa.DESPESA ? "DESPESA" : "RECEITA"; 
                Row linhaFluxo = sheet.createRow(linha);
                linha++;
                Cell cellFluxo = linhaFluxo.createCell(0);
                cellFluxo.setCellValue(StringUtils.leftPad(nomeFluxo, nomeFluxo.length() + 8));

                Map<String, List<ProdutoDuplicata>> mapaPorClassificacao = listaFluxo.stream()
                    .collect(Collectors.groupingBy(prod -> prod.getProduto().getClassificacao().getNomeClassificacao()));

                for (String classificacao : mapaPorClassificacao.keySet()) {
                    Row linhaClassificacao = sheet.createRow(linha);
                    linha++;
                    Cell cellClassificacao = linhaClassificacao.createCell(0);
                    cellClassificacao.setCellValue(StringUtils.leftPad(classificacao, classificacao.length() + 16));
                    
                    List<ProdutoDuplicata> listaClassificacao = mapaPorClassificacao.getOrDefault(classificacao, Collections.emptyList());
                    Map<Integer,List<ProdutoDuplicata>> mapaAno = listaClassificacao.stream()
                        .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getYear()));

                    for (int ano : mapaAno.keySet()) {
                        List<ProdutoDuplicata> listaAno = mapaPorAno.getOrDefault(ano, Collections.emptyList());
                        Map<Month, List<ProdutoDuplicata>> mapaPorMes = listaAno.stream()
                            .collect(Collectors.groupingBy(prodDup -> prodDup.getDuplicata().getDataVencimento().getMonth()));
                        for (Month mes : mapaPorMes.keySet()) {
                            List<ProdutoDuplicata> listaMes = mapaPorMes.getOrDefault(mes, Collections.emptyList());
                            double somaMes = listaMes.stream()
                                .mapToDouble(prodDup -> prodDup.getProduto().getValorTotal())
                                .sum();
                            String nomeColuna = mes.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.of("pt", "BR")) + "  " + ano;
                            int colunaClassificacao = mapaColuna.get(nomeColuna);
                            System.out.println(colunaClassificacao + " - " + nomeColuna + ":" + classificacao + " - " + somaMes);
                            Cell cellValor = linhaClassificacao.createCell(colunaClassificacao);
                            cellValor.setCellValue(somaMes);
                        }
                    }

                }
            }

        }
        
    }
}
