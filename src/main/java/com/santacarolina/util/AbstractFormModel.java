package com.santacarolina.util;

import com.santacarolina.interfaces.FormListener;
import com.santacarolina.interfaces.FormModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFormModel implements FormModel {

    private List<FormListener> listeners = new ArrayList<>();

    public abstract boolean updatingNotAllowed();

    @Override
    public void addListener(FormListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(FormListener listener) { listeners.remove(listener); }

    @Override
    public void fireChange(String... properties) { for (String property : properties) listeners.forEach(f -> f.update(property)); }

}
