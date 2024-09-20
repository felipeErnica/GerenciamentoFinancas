package com.santacarolina.util;

import jnafilechooser.api.JnaFileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileManager {

    private static final Logger logger = LogManager.getLogger();

    public static Optional<String> getFile() {
        JnaFileChooser fileChooser = new JnaFileChooser();
        fileChooser.setTitle("Selecionar Arquivo");
        fileChooser.addFilter("Arquivo PDF", "pdf");
        fileChooser.showOpenDialog(null);
        if (fileChooser.getSelectedFile() != null) {
            return Optional.of(fileChooser.getSelectedFile().getAbsolutePath());
        } else return Optional.empty();
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
