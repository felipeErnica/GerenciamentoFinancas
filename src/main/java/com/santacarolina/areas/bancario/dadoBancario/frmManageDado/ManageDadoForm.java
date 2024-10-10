package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.dao.DadoDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

import java.awt.*;

public class ManageDadoForm {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        EventQueue.invokeLater(ManageDadoForm::new);
    }

    public ManageDadoForm() {
        try {
            TableModel model = new TableModel();
            FormView view = new FormView();
            FormController controller = new FormController(model, view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}
