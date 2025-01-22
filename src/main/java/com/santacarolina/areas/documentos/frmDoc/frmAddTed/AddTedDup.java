package com.santacarolina.areas.documentos.frmDoc.frmAddTed;

import java.util.ArrayList;
import java.util.List;

import com.formdev.flatlaf.FlatLightLaf;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.CustomErrorThrower;

public class AddTedDup {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        AddTedDup.open(new ArrayList<>(), new DocumentoFiscal());
    }

    public static void open(List<DuplicataDTO> list, DocumentoFiscal documento) {
        try {
            FormView view = new FormView();
            FormModel model = new FormModel(list, documento);
            FormController controller = new FormController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
