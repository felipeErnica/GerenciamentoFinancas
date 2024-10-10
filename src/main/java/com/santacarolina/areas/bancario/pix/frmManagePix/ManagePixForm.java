package com.santacarolina.areas.bancario.pix.frmManagePix;

import com.formdev.flatlaf.FlatDarkLaf;
import com.santacarolina.dao.PixDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

public class ManagePixForm {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new ManagePixForm();
    }

    public ManagePixForm() {
        try {
            PixTableModel model = new PixTableModel(new PixDAO().findAll());
            ManagePixView view = new ManagePixView();
            ManagePixController controller = new ManagePixController(model, view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
}