package com.santacarolina.util;

import com.santacarolina.exceptions.CustomException;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class CustomErrorThrower {

    public static void throwError(CustomException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
    }

    public static void throwError(CustomException e, Logger logger, JDialog dialog) {
        logger.error(e.getMessage());
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessageTitle(), JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
    }

}
