package com.santacarolina.model.tablemodels;

import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DuplicataTableModel extends AbstractCustomModel<Duplicata> {

    private List<Duplicata> duplicataList = new ArrayList<>();
    private DocumentoFiscal documentoFiscal;

    private final String[] columns = {
            "Parcela",
            "Conta Bancária",
            "Data de Vencimento",
            "Método de Pagamento",
            "Valor Total",
            "Caminho do Boleto"
    };

    @Override
    public int getRowCount() {
        return duplicataList.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0,1,3,5 -> false;
            default -> true;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Duplicata d = duplicataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getNumDup();
            case 1 -> documentoFiscal.getPastaContabil() == null ? "" : documentoFiscal.getPastaContabil().getContaBancaria();
            case 2 -> d.getDataVencimento();
            case 3 -> d.getTipoPagamento();
            case 4 -> d.getValor();
            case 5 -> d.getBoletoCaminho();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1, 5 -> String.class;
            case 2 -> LocalDate.class;
            case 3 -> TipoPagamento.class;
            case 4 -> Double.class;
            default -> Object.class;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Duplicata duplicata = duplicataList.get(rowIndex);
        String value = String.valueOf(aValue);
        switch (columnIndex) {
            case 2 -> setDataVenc(duplicata,value);
            case 4 -> {
                if (value.isEmpty()) duplicata.setValor(0);
                else duplicata.setValor(Double.parseDouble(value));
            }
        }
    }

    private void setDataVenc(Duplicata duplicata, String value) {
        try {

            if (value.isEmpty()) duplicata.setDataVencimento(null);
            else duplicata.setDataVencimento(LocalDate.parse(value));

            if (duplicataList.stream().noneMatch(d -> d.getDataVencimento() == null)) {
                duplicataList.sort(Comparator.comparing(Duplicata::getDataVencimento));
                duplicataList.forEach(d -> d.setNumDup(duplicataList.indexOf(d) + 1));
                fireTableDataChanged();
            }

        } catch (DateTimeParseException  e) {
            e.printStackTrace();
            duplicata.setDataVencimento(null);
        }
    }

    public Duplicata getObject(int rowIndex){
        return duplicataList.get(rowIndex);
    }

    public void addRow(Duplicata d){
        documentoFiscal.addDuplicata(d);
        fireTableRowsInserted(getRowCount(),getRowCount());
    }

    @Override
    public void addNewRow() {
        addRow(new Duplicata());
    }

    public void removeRow(int row){
        Duplicata d = duplicataList.get(row);
        documentoFiscal.removeDuplicata(d);
        fireTableRowsDeleted(row,row);
    }

    @Override
    public void removeRows(int[] rows) {

    }

    @Override
    public void requeryTable() { }

    public List<Duplicata> getDuplicataList() {
        return duplicataList;
    }

    public void setDocumentoFiscal(DocumentoFiscal documentoFiscal) {
        this.documentoFiscal = documentoFiscal;
        duplicataList = documentoFiscal.getDuplicatas();
    }

}
