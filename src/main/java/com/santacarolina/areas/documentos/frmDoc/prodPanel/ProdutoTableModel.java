package com.santacarolina.areas.documentos.frmDoc.prodPanel;

import com.santacarolina.dao.ClassificacaoDAO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.EditTableModel;
import com.santacarolina.model.ClassificacaoContabil;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Produto;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.StringConversor;

import javax.swing.*;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class ProdutoTableModel implements EditTableModel<Produto> {

    private ProdModel formModel;

    private CustomTableModelImpl<Produto> tableModel;
    private DocumentoFiscal documentoFiscal;
    private List<Produto> produtoList;

    public ProdutoTableModel(DocumentoFiscal documentoFiscal, ProdModel formModel) {
        produtoList = documentoFiscal.getProdutos();
        this.documentoFiscal = documentoFiscal;
        tableModel = new CustomTableModelImpl<>(this, produtoList);
        this.formModel = formModel;
    }

    private final String[] columns = {
            "Classificação",
            "Descrição",
            "Unidade",
            "Quantidade",
            "Valor Unitário",
            "Valor Total"
    };

    public List<Produto> getProdutoList() { return produtoList; }

    @Override
    public CustomTableModelImpl<Produto> getBaseModel() { return tableModel; }

    @Override
    public int getRowCount() { return produtoList.size(); }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return columnIndex != 5  && columnIndex != 0; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto p = produtoList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getClassificacao() != null ? p.getClassificacao().getNumeroIdentificacao() : "";
            case 1 -> p.getDescricao();
            case 2 -> p.getUnd();
            case 3 -> p.getQuantidade();
            case 4 -> StringConversor.getCurrency(p.getValorUnit());
            case 5 -> StringConversor.getCurrency(p.getValorTotal());
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2, 4, 5 -> String.class;
            case 3 -> Double.class;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Produto p = produtoList.get(rowIndex);
        String value = aValue != null ? String.valueOf(aValue) : "";
        switch (columnIndex) {
            case 0 -> {
                if (value != null) findClassificacao(value, p);
                else p.setClassificacao(null);
            }
            case 1 ->  p.setDescricao(value.toUpperCase());
            case 2 -> p.setUnd(value.toUpperCase());
            case 3 -> {
                try {
                    p.setQuantidade(StringConversor.getDoubleFromLocalFormat(value));
                    formModel.calculateValorTotal();
                } catch (ParseException e) {
                    p.setQuantidade(0);
                }
            }
            case 4 -> {
                try {
                    p.setValorUnit(StringConversor.getDoubleFromLocalFormat(value));
                    if (documentoFiscal.getFluxoCaixa() == FluxoCaixa.DESPESA) p.setValorUnit(Math.abs(p.getValorUnit())*-1);
                    else p.setValorUnit(Math.abs(p.getValorUnit()));
                    formModel.calculateValorTotal();
                } catch (ParseException e) {
                    p.setValorUnit(0);
                }
            }
        }
    }

    private void findClassificacao(String aValue, Produto p) {
        try {
            ClassificacaoDAO controller = new ClassificacaoDAO();
            Optional<ClassificacaoContabil> optional = controller.findByNumero(aValue);
            optional.ifPresentOrElse(p::setClassificacao,() -> p.setClassificacao(null));
        } catch (FetchFailException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
            p.setClassificacao(null);
        }
    }

    public Produto getObject(int rowIndex) { return produtoList.get(rowIndex); }

    public void addRow(Produto produto) {
        tableModel.addRow(produto);
        fireTableDataChanged();
    }

    @Override
    public void addNewRow() {
        Produto p = new Produto();
        p.setDocumento(documentoFiscal);
        addRow(p);
    }

    @Override
    public void removeRows(int[] rows) {
        tableModel.removeRows(rows);
        fireTableDataChanged();
        formModel.calculateValorTotal();
    }

    public void fireTableDataChanged() { tableModel.fireTableDataChanged(); }

    @Override
    public void fireTableCellUpdated(int row, int column) { tableModel.fireTableCellUpdated(row, column); }

}
