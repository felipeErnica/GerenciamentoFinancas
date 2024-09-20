package com.santacarolina.areas.mainFrame.pgProdutos;

import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.ui.CustomTableModelImpl;

import java.time.LocalDate;
import java.util.List;

public class ProdTableModel implements CustomTableModel<ProdutoDTO> {

    private CustomTableModelImpl<ProdutoDTO> baseModel;
    private List<ProdutoDTO> produtoList;

    public ProdTableModel (List<ProdutoDTO> produtoList) {
        this.baseModel = new CustomTableModelImpl<>(this, produtoList);
        this.produtoList = produtoList;
    }

    @Override
    public CustomTableModelImpl<ProdutoDTO> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return baseModel.getRowCount(); }

    @Override
    public int getColumnCount() { return 9; }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Data de Emissão";
            case 1 -> "Pasta Contábil";
            case 2 -> "Emitente";
            case 3 -> "Tipo de Mercadoria";
            case 4 -> "Descrição";
            case 5 -> "Unidade";
            case 6 -> "Quantidade";
            case 7 -> "Valor Unitário";
            case 8 -> "Valor Total";
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> LocalDate.class;
            case 1, 2, 3, 4, 5 ->  String.class;
            case 6, 7, 8 -> Double.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProdutoDTO p = produtoList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.dataEmissao();
            case 1 -> p.nomePasta();
            case 2 -> p.nomeContato();
            case 3 -> p.classificacao();
            case 4 -> p.descricao();
            case 5 -> p.und();
            case 6 -> p.quantidade();
            case 7 -> p.valorUnit();
            case 8 -> p.valorUnit()*p.quantidade();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public ProdutoDTO getObject(int rowIndex) { return produtoList.get(rowIndex); }

}
