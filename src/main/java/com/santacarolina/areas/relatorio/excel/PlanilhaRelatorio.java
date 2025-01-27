package com.santacarolina.areas.relatorio.excel;

import java.util.Collections;
import java.util.List;
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
                String nomeFluxo = fluxo == FluxoCaixa.DESPESA ? "DESPESA" : "RECEITA"; Row linhaFluxo = sheet.createRow(linha);
                linha++;
                Cell cellFluxo = linhaFluxo.createCell(0);
                cellFluxo.setCellValue(StringUtils.leftPad(nomeFluxo, 2));

                Map<String, List<ProdutoDuplicata>> mapaPorClassificacao = listaFluxo.stream()
                    .collect(Collectors.groupingBy(prod -> prod.getProduto().getClassificacao().getNomeClassificacao()));

                for (String classificacao : mapaPorClassificacao.keySet()) {
                    Row linhaClassificacao = sheet.createRow(linha);
                    linha++;
                    Cell cellClassificacao = linhaClassificacao.createCell(0);
                    cellClassificacao.setCellValue(StringUtils.leftPad(classificacao, 4));
                }
            }

        }
        
    }
}
