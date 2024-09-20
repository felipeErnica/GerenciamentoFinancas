package com.santacarolina.ui;

import javax.swing.*;
import java.awt.*;

public class AddView {

    private JDialog dialog;
    private JButton addButton;
    private JButton closeButton;

    public AddView() {
        initComponents();
    }

    private void initComponents() {

        dialog = new JDialog();
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        Dimension buttonDimension = new Dimension(200, 30);

        JPanel buttonPane = new JPanel();
        addButton = new JButton();
        addButton.setPreferredSize(buttonDimension);
        closeButton = new JButton("Cancelar");
        closeButton.setPreferredSize(buttonDimension);
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(addButton);
        buttonPane.add(closeButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(buttonPane, BorderLayout.SOUTH);
    }

    public JDialog getDialog() { return dialog; }
    public JButton getAddButton() { return addButton; }

}