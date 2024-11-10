package com.santacarolina.interfaces;

import java.awt.event.MouseEvent;
import java.util.List;

public interface ManageController<T> extends Controller {
    void table_onDoubleClick(MouseEvent e);
    void addButton_onClick();
    void callDeleteDAO(List<T> list);
}
