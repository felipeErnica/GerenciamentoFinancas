package com.santacarolina.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OptionDialog {

    private static int result = -1;

    public static int showReplaceDialog(String message) {
        message += "\nDeseja substituir os dados existentes por estes?";
        return OptionDialog.showOptionDialog(message, "Registro já existe!");
    }

    public static void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, "ERRO: " + title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessSaveMessage() {
        JOptionPane.showMessageDialog(null,
                "Informações salvas com sucesso!",
                "Informação Salva!",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static int showDeleteDialog (int numDeletions) {
        String message = numDeletions == 1 ? "Deseja excluir este registro?" :
                "Deseja excluir estes " + numDeletions + " registros?";

        return OptionDialog.showOptionDialog(message,"Exclusão de Registros!");
    }

    public static int showDeleteCascadeDialog (int numDeletions) {
        String message = numDeletions == 1 ? "Deseja excluir este registro?" :
                "Deseja excluir estes " + numDeletions + " registros?";
        message = message + "\n\n ATENÇÃO: Estes registros estão relacionados a outros dados no sistema! " + 
            "Ao excluí-los estes dados serão apagados também! " + 
            "Deseja continuar mesmo assim?";
        return OptionDialog.showOptionDialog(message,"Exclusão de Registros!");
    }

    public static int showOptionDialog(String message, String title) {

        JButton yesButton = new JButton("Sim");
        JButton noButton = new JButton("Não");
        JButton[] buttons = new JButton[]{yesButton,noButton};

        JOptionPane optionPane = new JOptionPane(message,
                JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, buttons, yesButton);
        Dialog dialog = optionPane.createDialog("ATENÇÃO: " + title);

        KeyStroke ksYes = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false);
        KeyStroke ksNo = KeyStroke.getKeyStroke(KeyEvent.VK_N, 0, false);
        Action actionYes = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = JOptionPane.YES_OPTION;
                dialog.dispose();
            }
        };
        Action actionNo = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = JOptionPane.NO_OPTION;
                dialog.dispose();
            }
        };

        yesButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ksYes,"click button");
        yesButton.getActionMap().put("click button", actionYes);
        yesButton.addActionListener(actionYes);

        noButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ksNo, "no");
        noButton.getActionMap().put("no", actionNo);
        noButton.addActionListener(actionNo);

        dialog.setModal(true);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setVisible(true);

        return result;
    }

}
