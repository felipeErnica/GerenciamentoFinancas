package com.santacarolina.areas.relatorio.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.santacarolina.areas.relatorio.FormModel;
import com.santacarolina.model.ProdutoDuplicata;

public class ExportExcel {

    public static void exportToExcel(FormModel model) throws IOException {
        List<ProdutoDuplicata> listaRelatorio = model.getListaFiltrada();
        Workbook workbook = new XSSFWorkbook();
            
        listaRelatorio = listaRelatorio.stream()
            .sorted(Comparator.comparing(prodDup -> prodDup.getDuplicata().getDataVencimento()))
            .collect(Collectors.toList());

        PlanilhaRelatorio.criaPlanilha(workbook, listaRelatorio);
        PlanilhaProdutos.criaPlanilha(workbook, listaRelatorio);

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
