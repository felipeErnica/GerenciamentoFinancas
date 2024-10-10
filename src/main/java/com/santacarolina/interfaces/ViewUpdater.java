package com.santacarolina.interfaces;

import java.beans.PropertyChangeListener;

public interface ViewUpdater {
    void addPropertyChangeListener(PropertyChangeListener listener);
    void fireInitialChanges();
}
