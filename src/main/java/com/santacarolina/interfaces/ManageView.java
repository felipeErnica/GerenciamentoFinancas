package com.santacarolina.interfaces;

import com.santacarolina.ui.ActionSVGButton;

import javax.swing.*;

public interface ManageView {
    JTable getTable();
    JDialog getDialog();
    ActionSVGButton getAddButton();
    ActionSVGButton getDeleteButton();
    void formatColumns();
}
