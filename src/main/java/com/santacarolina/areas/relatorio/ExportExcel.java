package com.santacarolina.areas.relatorio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.santacarolina.model.ProdutoDuplicata;

public class ExportExcel {

    private static String[] nomeColunas = {
        "Data de Emissão",
        "Pasta Contábil",
        "Fluxo de Caixa",
        "Classificação Contábil",
        "Núm. da Parcela",
        "Emitente",
        "Descrição",
        "UND",
        "Quant.",
        "Valor Unit.",
        "Valor Total"
    };

    public static void exportToExcel(FormModel model) throws IOException {
        List<ProdutoDuplicata> listaRelatorio = model.getListaFiltrada();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produtos e Serviços");

        Row header = sheet.createRow(0);
        
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        
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

        for (int linha = 0; linha < listaRelatorio.size(); linha++) {
            ProdutoDuplicata produtoDuplicata = listaRelatorio.get(linha);
            Row row = sheet.createRow(linha + 1);
            
            Cell data = row.createCell(0);
            Cell pasta = row.createCell(1);
            Cell fluxo = row.createCell(2);
            Cell classificacao = row.createCell(3);
            Cell numDup = row.createCell(4);
            Cell emissor = row.createCell(5);
            Cell descricao = row.createCell(6);
            Cell und = row.createCell(7);
            Cell quant = row.createCell(8);
            Cell valorUnit = row.createCell(9);
            Cell valorTotal = row.createCell(10);

            data.setCellValue(produtoDuplicata.getDuplicata().getDataVencimento());
            pasta.setCellValue(produtoDuplicata.getDuplicata().getDocumento().getPasta().getNome());
            fluxo.setCellValue(produtoDuplicata.getProduto().getClassificacao().getCategoria().getFluxoCaixa().toString());
            classificacao.setCellValue(produtoDuplicata.getProduto().getClassificacao().getNomeClassificacao());
            numDup.setCellValue(produtoDuplicata.getDuplicata().getNumDup());
            emissor.setCellValue(produtoDuplicata.getDuplicata().getDocumento().getEmissor().getNome());
            descricao.setCellValue(produtoDuplicata.getProduto().getDescricao());
            und.setCellValue(produtoDuplicata.getProduto().getUnd());
            quant.setCellValue(produtoDuplicata.getProduto().getQuantidade());
            valorUnit.setCellValue(produtoDuplicata.getProduto().getValorUnit());
            valorTotal.setCellValue(produtoDuplicata.getProduto().getValorTotal());

            data.setCellStyle(dataStyle);
            pasta.setCellStyle(cellStyle);
            fluxo.setCellStyle(cellStyle);
            classificacao.setCellStyle(cellStyle);
            numDup.setCellStyle(cellStyle);
            emissor.setCellStyle(cellStyle);
            descricao.setCellStyle(cellStyle);
            und.setCellStyle(cellStyle);
            quant.setCellStyle(cellStyle);
            valorUnit.setCellStyle(currencyStyle);
            valorTotal.setCellStyle(currencyStyle);
        }

        Locale localizacaoPt = Locale.of("pt", "BR");
        String mesInicial = model.getDataInicio().getMonth().getDisplayName(TextStyle.SHORT, localizacaoPt) + " " + model.getDataInicio().getYear();
        String mesFinal = model.getDataFim().getMonth().getDisplayName(TextStyle.SHORT, localizacaoPt) + " " + model.getDataFim().getYear();
        String nomeArquivo = "RELATÓRIO " + mesInicial.toUpperCase() + " - " + mesFinal.toUpperCase() + ".xlsx"; 

        String caminhoString = model.getCaminho() + "/" + nomeArquivo; 
        Path caminho = Paths.get(caminhoString);
        Files.deleteIfExists(caminho);

        FileOutputStream excelFile = new FileOutputStream(caminho.toString());
        workbook.write(excelFile);
        workbook.close();
        excelFile.close();
    }

}
