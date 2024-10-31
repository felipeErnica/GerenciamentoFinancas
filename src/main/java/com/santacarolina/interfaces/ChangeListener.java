package com.santacarolina.interfaces;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * UpdateListener
 */
public interface ChangeListener extends DocumentListener {

    @Override
    default void changedUpdate(DocumentEvent e) { update(e); }

    @Override
    default void insertUpdate(DocumentEvent e) { update(e); }

    @Override
    default void removeUpdate(DocumentEvent e) { update(e); }
    
    void update(DocumentEvent e);
}
