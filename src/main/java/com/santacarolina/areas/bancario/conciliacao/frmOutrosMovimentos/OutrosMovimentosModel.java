package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import com.santacarolina.areas.bancario.conciliacao.extratoConciliacao.ExtratoConciliacaoTableModel;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class OutrosMovimentosModel implements ViewUpdater {

    public static final String REQUERY = "requery";

    private TipoMovimento tipoMovimento;
    private ExtratoDAO extratoDao;
    private ExtratoConciliacaoTableModel extratoTableModel;
    private PropertyFirer ps;

    public OutrosMovimentosModel() throws FetchFailException {
        ps = new PropertyFirer(this);
        extratoDao = new ExtratoDAO();
        extratoTableModel = new ExtratoConciliacaoTableModel(extratoDao.findByConciliacao(false));
    }

    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public Extrato getExtrato(int rowIndex) { return extratoTableModel.getObject(rowIndex).fromDTO(); }
    public ExtratoConciliacaoTableModel getExtratoTableModel() { return extratoTableModel; }
    public void addPropertyChangeListener(PropertyChangeListener listener) { ps.addPropertyChangeListener(listener); }

    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    @Override
    public void fireInitialChanges() {}

    public void requeryTables () throws FetchFailException {
        extratoTableModel.setList(extratoDao.findByConciliacao(false));
        ps.firePropertyChange(REQUERY, true);
    }

}
