package com.santacarolina.areas.relatorio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
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

    public static void exportToExcel(List<ProdutoDuplicata> listaRelatorio) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row header = sheet.createRow(0);
        
        for (int coluna = 0; coluna < nomeColunas.length; coluna++) {
            Cell cell = header.createCell(coluna);
            cell.setCellValue(nomeColunas[coluna]);
        }

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
        }

        Path caminho = Paths.get("C:/Users/Fazenda/Downloads/teste.xlsx");
        Files.deleteIfExists(caminho);

        FileOutputStream excelFile = new FileOutputStream(caminho.toString());
        workbook.write(excelFile);
        workbook.close();
    }

}
