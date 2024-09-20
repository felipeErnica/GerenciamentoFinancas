package com.santacarolina.areas.bancario.conciliacao.frmConciliacao;

import com.santacarolina.areas.mainFrame.pgBanco.ExtratoTableModel;
import com.santacarolina.areas.mainFrame.pgDuplicatas.DupTableModel;
import com.santacarolina.dao.DuplicataDao;
import com.santacarolina.dao.ExtratoDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.beans.Extrato;
import com.santacarolina.util.PropertyFirer;

import java.beans.PropertyChangeListener;

public class ConciliacaoModel implements NewFormModel {

    public static final String TABLES = "table";
    public static final String REQUERY = "requery";

    public static final String ONE_TO_ONE = "oneToOne";
    public static final String ONE_DUP_MANY_EXTRATO = "manyExtrato";
    public static final String ONE_EXTRATO_MANY_DUP = "manyDup";

    private ExtratoDao extratoDao;
    private DuplicataDao duplicataDao;
    private ExtratoTableModel extratoTableModel;
    private DupTableModel dupTableModel;
    private String tableSelection;
    private PropertyFirer ps;

    public ConciliacaoModel() throws FetchFailException {
        ps = new PropertyFirer(this);
        extratoDao = new ExtratoDao();
        duplicataDao = new DuplicataDao();
        extratoTableModel = new ExtratoTableModel(extratoDao.findByConciliacao(false));
        dupTableModel = new DupTableModel(duplicataDao.findByPayed(false));
        tableSelection = ONE_TO_ONE;
    }

    public Extrato getExtrato(int rowIndex) { return new Extrato().fromDTO(extratoTableModel.getObject(rowIndex)); }
    public Duplicata getDuplicata(int rowIndex) { return new Duplicata().fromDTO(dupTableModel.getObject(rowIndex)); }
    public ExtratoTableModel getExtratoTableModel() { return extratoTableModel; }
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
        dupTableModel.setList(duplicataDao.findByPayed(false));
        ps.firePropertyChange(REQUERY, true);
    }

}
