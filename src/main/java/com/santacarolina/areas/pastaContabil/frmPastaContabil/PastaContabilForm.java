package com.santacarolina.areas.pastaContabil.frmPastaContabil;

import com.santacarolina.GerenciamentoFinancas;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.PastaContabil;
import com.santacarolina.util.CustomErrorThrower;

public class PastaContabilForm {

    //Modo Teste
    public static void main(String[] args) {
        try {
            GerenciamentoFinancas.setStyle();
            open(new PastaDAO().findById(1).get());
        } catch (FetchFailException ignored) {
        }
    }

    // Método para abrir o formulário em modo de adição de uma nova pasta contábil
    public static void openNew() {
        PastaContabilView view = new PastaContabilView("Adicionar Pasta", "Nova Pasta Contábil");
        buildForm(view, new PastaContabil());
    }

    // Método para abrir o formulário para editar uma pasta contábil existente
    public static void open(PastaContabil pasta) {
        PastaContabilView view = new PastaContabilView("Salvar Pasta", "Editar Pasta Contábil");
        PastaContabil clone = pasta.clone();
        buildForm(view, clone);
    }

    // Método privado para construir o formulário com a visão e o modelo da pasta contábil
    private static void buildForm(PastaContabilView view, PastaContabil pasta) {
        try {
            PastaContabilModel model = new PastaContabilModel(pasta);
            PastaContabilController controller = new PastaContabilController(view, model);
            model.addPropertyChangeListener(view);
            controller.showView();
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
        }
    }

}
