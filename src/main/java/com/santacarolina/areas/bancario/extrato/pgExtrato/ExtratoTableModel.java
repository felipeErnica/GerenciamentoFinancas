package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.time.LocalDate;
import java.util.List;

import javax.swing.event.TableModelListener;

import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.CustomTableModel;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.CustomTableModelImpl;

public class ExtratoTableModel implements CustomTableModel<ExtratoDTO> {

    public static final String CONTA = "conta";

    private ExtratoDAO dao;
    private ContaBancaria contaBancaria;
    private List<ExtratoDTO> extratoList;
    private CustomTableModelImpl<ExtratoDTO> model;

    public ExtratoTableModel(List<ExtratoDTO> extratoList) {
        this.extratoList = extratoList;
        this.model = new CustomTableModelImpl<>(this, extratoList);
        this.dao = new ExtratoDAO();
    }

    public void setContaBancaria(ContaBancaria contaBancaria) throws FetchFailException {
        this.contaBancaria = contaBancaria;
        extratoList = dao.findByConta(contaBancaria.getId());
        model.setList(extratoList);
    }

    @Override
    public CustomTableModelImpl<ExtratoDTO> getBaseModel() { return model; }

    public int getRowCount() { return extratoList.size(); }
    public int getColumnCount() { return 6; }
    public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    public ExtratoDTO getObject(int rowIndex) { return extratoList.get(rowIndex); }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
    public void addTableModelListener(TableModelListener l) { model.addTableModelListener(l); }
    public void removeTableModelListener(TableModelListener l) { model.removeTableModelListener(l); }
    public ContaBancaria getContaBancaria() { return contaBancaria; }

    public void setList(List<ExtratoDTO> list) {
        extratoList = list;
        model.setList(list);
    }

    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "";
            case 1 -> "Data da Transação";
            case 2 -> "Conta Bancária";
            case 3 -> "Categoria Bancária";
            case 4 -> "Descrição";
            case 5 -> "Valor";
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 1 -> LocalDate.class;
            case 2, 3, 4 -> String.class;
            case 5 -> Double.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ExtratoDTO e = extratoList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> e.getDataTransacao();
            case 2 -> e.getContaBancaria();
            case 3 -> e.getCategoriaExtrato();
            case 4 -> e.getDescricao();
            case 5 -> e.getValor();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

}
