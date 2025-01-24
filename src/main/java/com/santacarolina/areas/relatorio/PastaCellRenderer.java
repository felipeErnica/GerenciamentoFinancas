package com.santacarolina.areas.relatorio;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.santacarolina.model.PastaContabil;

public class PastaCellRenderer implements ListCellRenderer<PastaContabil> {

    @Override
    public Component getListCellRendererComponent(JList<? extends PastaContabil> listaPasta, PastaContabil pasta, int arg2,
            boolean arg3, boolean arg4) {
        JCheckBox checkBox = new JCheckBox(pasta.getNome());
        return checkBox;
    }

}
