package com.santacarolina.areas.bancario.extrato.pgExtrato;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.dto.ExtratoDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.ui.CustomTableModelImpl;
import com.santacarolina.util.PropertyFirer;
import com.santacarolina.util.StringConversor;

/**
 * ExtratoModel
 */
public class ExtratoModel implements ViewUpdater {
    
    public static final String SALDO = "saldo";

    private ExtratoTableModel tableModel;
    private ContaBancaria contaBancaria;
    private String saldo;
    private List<ExtratoDTO> list;

    private PropertyFirer pf;

    public ExtratoModel() {
        tableModel = new ExtratoTableModel();
        pf = new PropertyFirer(this);
    }

    public void setContaBancaria(ContaBancaria contaBancaria) throws FetchFailException {
        this.contaBancaria = contaBancaria;
        list = new ExtratoDAO().findByConta(contaBancaria.getId());
        double saldoCalculo = list.stream()
            .mapToDouble(e -> e.getValor())
            .sum();
        saldo = StringConversor.getCurrency(saldoCalculo);
        tableModel.setList(list);
        pf.firePropertyChange(SALDO, saldo);
    }

    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public CustomTableModelImpl<ExtratoDTO> getBaseModel() { return tableModel.getBaseModel(); }
    public ExtratoTableModel getTableModel() { return tableModel; }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { pf.firePropertyChange(SALDO, saldo); }

}
