package com.santacarolina.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileManager {

    private static final Logger logger = LogManager.getLogger();

    public static Optional<String> getFile() {
        Display display = new Display();
        Shell shell = new Shell(display);
        FileDialog fileChooser = new FileDialog(shell);
        fileChooser.setText("Selecionar Arquivo");
        fileChooser.setFilterNames(new String[] {"Arquivo PDF"});
        fileChooser.setFilterExtensions(new String[] {"*.pdf"});
        Optional<String> optional = fileChooser.openDialog();
        shell.close();
        display.close();
        return optional;
    }

    public static void openFile(String path) {
        try {
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (IllegalArgumentException | IOException e) {
            if (e instanceof IllegalArgumentException) {
                JOptionPane.showMessageDialog(null,
                        "Arquivo não encontrado!",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível abrir o arquivo!",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
            logger.error(e.getMessage());
        }
    }

}
