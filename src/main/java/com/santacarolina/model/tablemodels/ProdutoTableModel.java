package com.santacarolina.model.tablemodels;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ClassificacaoContabil;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Produto;
import com.santacarolina.dao.ClassificacaoDao;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoTableModel extends AbstractCustomModel<Produto> {

    private DocumentoFiscal documentoFiscal;
    private List<Produto> produtoList = new ArrayList<>();

    private final String[] columns = {
            "Classificação",
            "Descrição",
            "Unidade",
            "Quantidade",
            "Valor Unitário",
            "Valor Total"
    };

    public DocumentoFiscal getDocumentoFiscal() { return documentoFiscal; }
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
            case 4 -> p.getValorUnit();
            case 5 -> p.getValorTotal();
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2 -> String.class;
            case 3, 4, 5 -> Double.class;
            default -> Object.class;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Produto p = produtoList.get(rowIndex);
        String value = String.valueOf(aValue);
        switch (columnIndex) {
            case 0 -> {
                if (!value.isEmpty()) findClassificacao(value, p);
                else p.setClassificacao(null);
            }
            case 1 ->  p.setDescricao(value);
            case 2 -> p.setUnd(value);
            case 3 -> {
                if (value.isEmpty()) p.setQuantidade(0);
                else p.setQuantidade(Double.parseDouble(value));
            }
            case 4 -> {
                if (value.isEmpty()) p.setValorUnit(0);
                else p.setValorUnit(Double.parseDouble(value));
            }
        }
    }

    private void findClassificacao(String aValue, Produto p) {
        try {
            ClassificacaoDao controller = new ClassificacaoDao();
            Optional<ClassificacaoContabil> optional = controller.getByNumero(aValue);
            optional.ifPresentOrElse(p::setClassificacao,() -> p.setClassificacao(null));
        } catch (FetchFailException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
            p.setClassificacao(null);
        }
    }

    public Produto getObject(int rowIndex){
        return produtoList.get(rowIndex);
    }

    @Override
    public void addRow(Produto p){
        documentoFiscal.addProduto(p);
        fireTableRowsInserted(getRowCount(),getRowCount());
    }

    @Override
    public void addNewRow() {
        addRow(new Produto());
    }

    @Override
    public void removeRow (int row){
        produtoList.remove(row);
        fireTableRowsDeleted(row,row);
    }

    @Override
    public void removeRows(int[] rows) {

    }

    @Override
    public void requeryTable() { }

    public void setDocumentoFiscal(DocumentoFiscal documentoFiscal) {
        this.documentoFiscal = documentoFiscal;
        this.produtoList = documentoFiscal.getProdutos();
    }

}
