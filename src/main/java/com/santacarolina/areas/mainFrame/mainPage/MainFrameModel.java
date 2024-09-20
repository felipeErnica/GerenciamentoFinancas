package com.santacarolina.areas.mainFrame.mainPage;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.util.PropertyFirer;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainFrameModel implements NewFormModel {

    public static final String CENTER_PANEL = "centerPanel";
    public static final String DUPLICATAS = "dups";
    public static final String PRODUTOS = "prods";
    public static final String BANCARIAS = "bancarias";

    private MainPaneView paneView;
    private PropertyFirer ps;

    public MainFrameModel() {
        this.ps = new PropertyFirer(this);
    }

    public void setCenterPanel(MainPaneView paneView) {
        this.paneView = paneView;
        ps.firePropertyChange(CENTER_PANEL, paneView);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        ps.addPropertyChangeListener(listener);
    }

    @Override
    public void fireInitialChanges() {

    }

}
