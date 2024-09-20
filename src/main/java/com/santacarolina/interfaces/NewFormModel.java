package com.santacarolina.interfaces;

import java.beans.PropertyChangeListener;

public interface NewFormModel {
    void addPropertyChangeListener(PropertyChangeListener listener);
    void fireInitialChanges();
}
