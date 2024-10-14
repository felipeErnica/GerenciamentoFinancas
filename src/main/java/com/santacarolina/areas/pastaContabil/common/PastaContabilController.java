package com.santacarolina.areas.pastaContabil.common;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.DoubleClickListener;
import com.santacarolina.util.ViewInvoker;

public class PastaContabilController {

    private PastaContabilView view;
    private PastaContabilModel model;

    public PastaContabilController(PastaContabilView view, PastaContabilModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() throws FetchFailException {
        view.getBankAccountComboBox().setModel(new ListComboBoxModel<>(new ContaDAO().findAll()));

        view.getPathTextField().addMouseListener((DoubleClickListener) e -> pathTextField_onDoubleClick());
        view.getFolderTextField().addFocusListener((AfterUpdateListener) e -> folderTextField_afterUpdate());
        view.getAddFolder().addActionListener(e -> addFolder_onClick());
    }

    private void folderTextField_afterUpdate() { model.setNomePasta(view.getFolderTextField().getText()); }

    private void pathTextField_onDoubleClick() {
    }
    
    private void addFolder_onClick() {
    }


    public void showView() { ViewInvoker.showView(view.getDialog()); }
     
}

