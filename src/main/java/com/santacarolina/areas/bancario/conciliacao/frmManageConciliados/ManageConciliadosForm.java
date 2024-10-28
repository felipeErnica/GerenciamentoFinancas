package com.santacarolina.areas.bancario.conciliacao.frmManageConciliados;

import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.util.CustomErrorThrower;

/**
 * ManageConciliadosForm
 */
public class ManageConciliadosForm {

    public static void open() {
        try {
            FormView view = new FormView();
            ConciliadosTableModel tableModel = new ConciliadosTableModel();
            FormController controller = new FormController(view, tableModel);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }
    
}
