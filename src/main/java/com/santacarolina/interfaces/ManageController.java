package com.santacarolina.interfaces;

import java.awt.event.MouseEvent;

public interface ManageController extends Controller{
    void table_onDoubleClick(MouseEvent e);
    void addButton_onClick();
    void deleteButton_onClick();
}
