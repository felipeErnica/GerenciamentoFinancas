package com.santacarolina.areas.bancario.dadoBancario.frmManageDado;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.dao.DadoDao;
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
            DadoTableModel model = new DadoTableModel(new DadoDao().findAll());
            ManageDadoView view = new ManageDadoView();
            ManageDadoController controller = new ManageDadoController(model, view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}
