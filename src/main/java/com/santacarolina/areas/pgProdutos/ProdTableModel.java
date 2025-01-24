package com.santacarolina.areas.pgProdutos;

import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.Produto;
import com.santacarolina.ui.CustomTableModelImpl;

import java.time.LocalDate;
import java.util.List;

public class ProdTableModel implements CustomTableModel<Produto> {

    private CustomTableModelImpl<Produto> baseModel;
    private List<Produto> list;
    private FilterModel filterModel;

    private String[] columnNames = {
        "Data de Emissão",
        "Pasta Contábil",
        "Emitente",
        "Tipo de Mercadoria",
        "Descrição",
        "Unidade",
        "Quantidade",
        "Valor Unitário",
        "Valor Total"
    };

    public ProdTableModel (List<Produto> produtoList) {
        this.baseModel = new CustomTableModelImpl<>(this, produtoList);
        this.list = produtoList;
        filterModel = new FilterModel(this);
    }

    @Override
    public CustomTableModelImpl<Produto> getBaseModel() { return baseModel; }

    @Override
    public int getRowCount() { return baseModel.getRowCount(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex) { return columnNames[columnIndex]; }

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
        Produto p = getObject(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getDocumento() != null ? p.getDocumento().getDataEmissao() : null;
            case 1 -> {
                if (p.getDocumento() == null) yield null;
                if (p.getDocumento().getPasta() == null) yield null;
                yield p.getDocumento().getPasta().getNome();
            }
            case 2 -> {
                if (p.getDocumento() == null) yield null;
                if (p.getDocumento().getEmissor() == null) yield null;
                yield p.getDocumento().getEmissor().getNome();
            }
            case 3 -> p.getClassificacao() != null ? p.getClassificacao().getNomeClassificacao(): null;
            case 4 -> p.getDescricao();
            case 5 -> p.getUnd();
            case 6 -> p.getQuantidade();
            case 7 -> p.getValorUnit();
            case 8 -> p.getValorTotal();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public Produto getObject(int rowIndex) { return baseModel.getObject(rowIndex); }

    public List<Produto> getList() { return list; }
    public FilterModel getFilterModel() { return filterModel; }

}
