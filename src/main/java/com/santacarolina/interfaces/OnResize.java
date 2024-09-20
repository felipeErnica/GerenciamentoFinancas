package com.santacarolina.interfaces;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public interface OnResize extends ComponentListener {

    @Override
    default void componentMoved(ComponentEvent e) {
    };

    @Override
    default void componentShown(ComponentEvent e) {
    };

    @Override
    default void componentHidden(ComponentEvent e) {
    };

    @Override
    void componentResized(ComponentEvent e);
}
