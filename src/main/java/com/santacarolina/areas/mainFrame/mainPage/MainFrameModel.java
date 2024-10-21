package com.santacarolina.areas.mainFrame.mainPage;

import java.beans.PropertyChangeListener;

import com.santacarolina.areas.mainFrame.common.MainPaneView;
import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.util.PropertyFirer;

public class MainFrameModel implements ViewUpdater {

    public static final String HOME_PAGE = "homePage";
    public static final String CENTER_PANEL = "centerPanel";
    public static final String DUPLICATAS = "dups";
    public static final String PRODUTOS = "prods";
    public static final String BANCARIAS = "bancarias";

    private PropertyFirer ps;

    public MainFrameModel() { this.ps = new PropertyFirer(this); }

    public void setCenterPanel(MainPaneView paneView) { ps.firePropertyChange(CENTER_PANEL, paneView); }
    public void setHomePage() { ps.firePropertyChange(HOME_PAGE, null); }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { ps.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() { ps.firePropertyChange(HOME_PAGE, null); }

}
