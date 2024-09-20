package com.santacarolina.util;

import com.santacarolina.interfaces.NewFormModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PropertyFirer {

    private PropertyChangeSupport ps;
    private NewFormModel model;

    public PropertyFirer(NewFormModel model) {
        ps = new PropertyChangeSupport(this);
        this.model = model;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        ps.addPropertyChangeListener(listener);
        model.fireInitialChanges();
    }

    public void firePropertyChange(String propertyName, Object newValue) {
        ps.firePropertyChange(propertyName, null,newValue);
    }

}
