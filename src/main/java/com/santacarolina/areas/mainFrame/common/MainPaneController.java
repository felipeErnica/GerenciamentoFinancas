package com.santacarolina.areas.mainFrame.common;

import java.util.List;

/**
 * MainPaneController
 * Interface necessária para indicar à classe de implementação as classes filhas.
 */
public interface MainPaneController<T> {
    void deleteBatch(List<T> list);
}
