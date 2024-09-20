package com.santacarolina.interfaces;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public interface OnPaneUpdate extends ContainerListener {

    @Override
    default void componentAdded(ContainerEvent e) { updateContainer(e); }

    @Override
    default void componentRemoved(ContainerEvent e) { updateContainer(e); }

    void updateContainer(ContainerEvent e);

}
