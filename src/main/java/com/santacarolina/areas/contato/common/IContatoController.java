package com.santacarolina.areas.contato.common;

import com.santacarolina.interfaces.Controller;

public interface IContatoController extends Controller {
    boolean nameExists();
    boolean docsExists();
    FormContatoModel getModel();
    FormContatoView getView();
}
