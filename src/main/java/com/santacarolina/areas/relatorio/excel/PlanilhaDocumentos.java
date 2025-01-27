package com.santacarolina.areas.relatorio.excel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.ProdutoDuplicata;

public class PlanilhaDocumentos {

    private static String[] nomeColunas = {
        "Data de Emissão",
        "Pasta Contábil",
        "Tipo de Documento",
        "Núm. do Documento",
        "Fluxo de Caixa",
        "Emitente",
        "Valor do Documento"
    };

    public static void criaPlanilha(Workbook workbook, List<ProdutoDuplicata> listaRelatorio) {
        
        Map<Long, DocumentoFiscal> mapDocumento = listaRelatorio.stream()
            .sorted(Comparator.comparing(prodDup -> prodDup.getProduto().getDocumento().getDataEmissao()))
            .collect(Collectors.toMap(prodDup -> prodDup.getProduto().getDocumento().getId(), 
                prodDup -> prodDup.getProduto().getDocumento(),
                (doc, equal) -> doc));

        Sheet sheet = workbook.createSheet("Documentos Fiscais");

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);

        sheet.createFreezePane(0, 1, 0, 1);

        for (int coluna = 0; coluna < nomeColunas.length; coluna++) {
            Cell cell = header.createCell(coluna);
            cell.setCellValue(nomeColunas[coluna]);
            cell.setCellStyle(headerStyle);
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        short dataFormat = workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy");

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.cloneStyleFrom(cellStyle);
        dataStyle.setDataFormat(dataFormat);

        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(cellStyle);
        currencyStyle.setAlignment(HorizontalAlignment.LEFT);
        currencyStyle.setDataFormat((short) 7);

        int linha = 1;

        for (long id : mapDocumento.keySet()) {
            preencheLinhas(linha, mapDocumento.get(id), sheet, cellStyle, dataStyle, currencyStyle);
            linha++;
        }

        for (int coluna = 0; coluna < nomeColunas.length; coluna++) sheet.autoSizeColumn(coluna);

    }

    private static void preencheLinhas(int linha, DocumentoFiscal doc, Sheet sheet, CellStyle... style) {
        CellStyle cellStyle = style[0];
        CellStyle dataStyle = style[1];
        CellStyle currencyStyle = style[2];

        Row row = sheet.createRow(linha);

        Cell data = row.createCell(0);
        Cell pasta = row.createCell(1);
        Cell tipoDoc = row.createCell(2);
        Cell numDoc = row.createCell(3);
        Cell fluxo = row.createCell(4);
        Cell emissor = row.createCell(5);
        Cell valor = row.createCell(6);

        data.setCellValue(doc.getDataEmissao());
        pasta.setCellValue(doc.getPasta().getNome());
        tipoDoc.setCellValue(doc.getTipoDoc().toString());
        numDoc.setCellValue(doc.getNumDoc() != null ? doc.getNumDoc().toString() : null);
        fluxo.setCellValue(doc.getFluxoCaixa().toString());
        emissor.setCellValue(doc.getEmissor().getNome());
        valor.setCellValue(doc.getValor());

        data.setCellStyle(dataStyle);
        pasta.setCellStyle(cellStyle);
        tipoDoc.setCellStyle(cellStyle);
        numDoc.setCellStyle(cellStyle);
        emissor.setCellStyle(cellStyle);
        fluxo.setCellStyle(currencyStyle);
        valor.setCellStyle(currencyStyle);
    }
    
}
