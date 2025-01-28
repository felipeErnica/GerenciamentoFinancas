package com.santacarolina.areas.relatorio;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.santacarolina.model.PastaContabil;

public class PastaCellRenderer implements ListCellRenderer<PastaContabil> {

    private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    

    @Override
    public Component getListCellRendererComponent(JList<? extends PastaContabil> listaPasta, PastaContabil pasta, int index,
            boolean isSelected, boolean cellHasFocus) {
        JCheckBox checkBox = (JCheckBox) defaultRenderer.getListCellRendererComponent(listaPasta, pasta, index, isSelected, cellHasFocus);
        return checkBox;
    }

}
