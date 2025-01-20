package com.santacarolina.areas.documentos.frmDoc.dupPanel;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoPagamento;
import com.santacarolina.interfaces.EditTableModel;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.StringConversor;

public class DuplicataTableModel implements EditTableModel<Duplicata> {

    private CustomTableModelImpl<Duplicata> tableModel;
    private List<Duplicata> duplicataList;
    private DocumentoFiscal documentoFiscal;
    private DupModel formModel;
    private TipoPagamento tipoPagamento;

    public DuplicataTableModel(DocumentoFiscal documentoFiscal, DupModel formModel) {
        this.duplicataList = documentoFiscal.getDuplicatas();
        this.tableModel = new CustomTableModelImpl<>(this, duplicataList);
        this.documentoFiscal = documentoFiscal;
        this.formModel = formModel;
        this.tipoPagamento = formModel.getTipoPagamento();
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        duplicataList.forEach(d -> d.setTipoPagamento(tipoPagamento));
        tableModel.fireTableDataChanged();
    }

    private final String[] columns = {
            "Parcela",
            "Conta Bancária",
            "Data de Vencimento",
            "Método de Pagamento",
            "Valor Total",
            "Caminho do Boleto"
    };

    @Override
    public CustomTableModelImpl<Duplicata> getBaseModel() { return tableModel; }

    @Override
    public int getRowCount() { return duplicataList.size(); }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 3, 5 -> false;
            case 2, 4 -> true;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Duplicata d = duplicataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getNumDup();
            case 1 -> documentoFiscal.getPasta() == null ? "" : documentoFiscal.getPasta().getConta();
            case 2 -> d.getDataVencimento() != null ?
                    d.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
            case 3 -> d.getTipoPagamento();
            case 4 -> StringConversor.getCurrency(d.getValor());
            case 5 -> d.getBoletoCaminho();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1, 2, 4, 5-> String.class;
            case 3 -> TipoPagamento.class;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Duplicata d = duplicataList.get(rowIndex);
        String value = String.valueOf(aValue);

        switch (columnIndex) {
            case 2 -> {
                try {
                    d.setDataVencimento(StringConversor.transformDate(value));
                } catch (DateTimeException e) {
                    d.setDataVencimento(null);
                }
            }
            case 4 -> {
                try {
                    d.setValor(StringConversor.getDoubleFromLocalFormat(value));
                    if (documentoFiscal.getFluxoCaixa() == FluxoCaixa.DESPESA) d.setValor(Math.abs(d.getValor())*-1);
                    else d.setValor(Math.abs(d.getValor()));
                    formModel.calculateValorTotal();
                } catch (ParseException e) {
                    d.setValor(0d);
                }
            }
        }

    }

    @Override
    public Duplicata getObject(int rowIndex) { return duplicataList.get(rowIndex); }
    public List<Duplicata> getDuplicataList() { return duplicataList; }

    public void addRow(Duplicata d) {
        tableModel.addRow(d);
        d.setNumDup(duplicataList.indexOf(d) + 1);
        fireTableDataChanged();
    }

    @Override
    public void addNewRow() {
        Duplicata d = new Duplicata();
        d.setTipoPagamento(tipoPagamento);
        d.setDocumento(documentoFiscal);
        addRow(d);
    }

    @Override
    public void removeRows(int[] rows) {
        tableModel.removeRows(rows);
        duplicataList.forEach(d -> d.setNumDup(duplicataList.indexOf(d) + 1));
        fireTableDataChanged();
        formModel.calculateValorTotal();
    }

    public void fireTableDataChanged() { tableModel.fireTableDataChanged(); }

    @Override
    public void fireTableCellUpdated(int row, int column) { tableModel.fireTableCellUpdated(row, column); }
}
