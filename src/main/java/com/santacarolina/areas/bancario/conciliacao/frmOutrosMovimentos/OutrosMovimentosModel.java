package com.santacarolina.areas.bancario.conciliacao.frmOutrosMovimentos;

import com.santacarolina.areas.mainFrame.pgBanco.ExtratoTableModel;
import com.santacarolina.areas.mainFrame.pgDuplicatas.DupTableModel;
import com.santacarolina.dao.ExtratoDao;
import com.santacarolina.enums.TipoMovimento;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class OutrosMovimentosModel implements NewFormModel {

    public static final String REQUERY = "requery";

    private TipoMovimento tipoMovimento;
    private ExtratoDao extratoDao;
    private ExtratoTableModel extratoTableModel;
    private PropertyFirer ps;

    public OutrosMovimentosModel() throws FetchFailException {
        ps = new PropertyFirer(this);
        extratoDao = new ExtratoDao();
        extratoTableModel = new ExtratoTableModel(extratoDao.findByConciliacao(false));
    }

    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public Extrato getExtrato(int rowIndex) { return new Extrato().fromDTO(extratoTableModel.getObject(rowIndex)); }
    public ExtratoTableModel getExtratoTableModel() { return extratoTableModel; }
    public void addPropertyChangeListener(PropertyChangeListener listener) { ps.addPropertyChangeListener(listener); }

    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    @Override
    public void fireInitialChanges() {}

    public void requeryTables () throws FetchFailException {
        extratoTableModel.setList(extratoDao.findByConciliacao(false));
        ps.firePropertyChange(REQUERY, true);
    }

}
