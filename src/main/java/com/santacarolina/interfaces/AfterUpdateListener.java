package com.santacarolina.interfaces;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface AfterUpdateListener extends FocusListener {
    @Override
    default void focusLost(FocusEvent e) { afterUpdtate(e); }
    @Override
    default void focusGained(FocusEvent e) {}
    void afterUpdtate(FocusEvent e);
}
