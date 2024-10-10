package com.santacarolina.util;

import com.santacarolina.interfaces.ViewUpdater;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PropertyFirer {

    private PropertyChangeSupport ps;
    private ViewUpdater model;

    public PropertyFirer(ViewUpdater model) {
        ps = new PropertyChangeSupport(this);
        this.model = model;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        ps.addPropertyChangeListener(listener);
        model.fireInitialChanges();
    }

    public void firePropertyChange(String propertyName, Object newValue) {
        ps.firePropertyChange(propertyName, null, newValue);
    }

}
