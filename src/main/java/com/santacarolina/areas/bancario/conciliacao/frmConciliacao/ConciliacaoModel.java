package com.santacarolina.areas.bancario.conciliacao.frmConciliacao;

import com.santacarolina.areas.bancario.conciliacao.ExtratoConciliacaoTableModel;
import com.santacarolina.areas.duplicatas.common.DupTableModel;
import com.santacarolina.dao.DuplicataDAO;
import com.santacarolina.dao.ExtratoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Extrato;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class ConciliacaoModel implements ViewUpdater {

    public static final String TABLES = "table";
    public static final String REQUERY = "requery";

    public static final String ONE_TO_ONE = "oneToOne";
    public static final String ONE_DUP_MANY_EXTRATO = "manyExtrato";
    public static final String ONE_EXTRATO_MANY_DUP = "manyDup";

    private ExtratoDAO extratoDao;
    private DuplicataDAO duplicataDao;
    private ExtratoConciliacaoTableModel extratoTableModel;
    private DupTableModel dupTableModel;
    private String tableSelection;
    private PropertyFirer ps;

    public ConciliacaoModel() throws FetchFailException {
        ps = new PropertyFirer(this);
        extratoDao = new ExtratoDAO();
        duplicataDao = new DuplicataDAO();
        extratoTableModel = new ExtratoConciliacaoTableModel(extratoDao.findByConciliacao(false));
        dupTableModel = new DupTableModel(duplicataDao.findNaoPagas());
        tableSelection = ONE_TO_ONE;
    }

    public Extrato getExtrato(int rowIndex) { return extratoTableModel.getObject(rowIndex).fromDTO(); }
    public Duplicata getDuplicata(int rowIndex) { return dupTableModel.getObject(rowIndex).fromDTO(); }
    public ExtratoConciliacaoTableModel getExtratoTableModel() { return extratoTableModel; }
    public DupTableModel getDupTableModel() { return dupTableModel; }
    public void addPropertyChangeListener(PropertyChangeListener listener) { ps.addPropertyChangeListener(listener); }

    public void setTableSelection(String tableSelection) {
        this.tableSelection = tableSelection;
        ps.firePropertyChange(TABLES, tableSelection);
    }

    @Override
    public void fireInitialChanges() { ps.firePropertyChange(TABLES, ONE_TO_ONE); }

    public void requeryTables () throws FetchFailException {
        extratoTableModel.setList(extratoDao.findByConciliacao(false));
        dupTableModel.setList(duplicataDao.findNaoPagas());
        ps.firePropertyChange(REQUERY, true);
    }

}
