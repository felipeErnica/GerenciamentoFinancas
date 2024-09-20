package com.santacarolina.interfaces;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface OnSelectListener extends FocusListener {
    @Override
    default void focusGained(FocusEvent focusEvent) {
        onSelect(focusEvent);
    }

    void onSelect(FocusEvent e);

    @Override
    default void focusLost(FocusEvent focusEvent) {
    }

}
