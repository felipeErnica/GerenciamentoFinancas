package com.santacarolina.areas.relatorio;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.santacarolina.areas.relatorio.excel.ExportExcel;
import com.santacarolina.dao.PastaDAO;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.interfaces.AfterUpdateListener;
import com.santacarolina.interfaces.Controller;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ViewInvoker;

@SuppressWarnings("unchecked")
public class FormController implements Controller {

    private FormView view;
    private FormModel model;

    public FormController(FormView view, FormModel model) throws FetchFailException {
        this.view = view;
        this.model = model;
        model.addPropertyChangeListener(view);
        init();
    }

    private void init() throws FetchFailException {
        PastaCellRenderer cellRenderer = new PastaCellRenderer();
        view.getListaPasta().setCellRenderer(cellRenderer);

        view.getCaminhoButton().addActionListener(e -> caminhoButton_onClick());
        view.getListaPasta().setModel(new ListComboBoxModel<>(new PastaDAO().findAll()));
        view.getRelatorioButton().addActionListener(e -> relatorioButton_onClick());
        view.getDataFim().addFocusListener((AfterUpdateListener) e -> dataFim_onLostFocus());
        view.getDataInicio().addFocusListener((AfterUpdateListener) e -> dataInicio_onLostFocus());
        view.getCaminho().addFocusListener((AfterUpdateListener) e -> caminho_onLostFocus());
    }

    private void caminhoButton_onClick() {
        EventQueue.invokeLater(() -> {
            Display display = new Display();
            Shell shell = new Shell(display);
            DirectoryDialog directoryDialog = new DirectoryDialog(shell);
            directoryDialog.setText("Selecionar Pasta");
            directoryDialog.openDialog().ifPresent(p -> model.selecionaCaminho(p));
            shell.close();
            display.close();
        });
    }

    private void caminho_onLostFocus() { model.setCaminho(view.getCaminho().getText()); }
    private void dataFim_onLostFocus() { model.setDataFim(view.getDataFim().getText()); }
    private void dataInicio_onLostFocus() { model.setDataInicio(view.getDataInicio().getText()); }

    private void relatorioButton_onClick() {
        EventQueue.invokeLater(() -> {
            try {
                if (!RelatorioValidator.validate(model)) return;
                ExportExcel.exportToExcel(model);
                JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso em " + model.getCaminho() + "!", 
                    "SUCESSO - Exportação de Relatório", 
                    JOptionPane.INFORMATION_MESSAGE);
                view.getDialog().dispose();
            } catch (IOException e) {
                OptionDialog.showErrorDialog("Não foi possível exportar o relatório", "ERRO: Exportação de Relatório");
            }
        });
    }

    @Override
    public void showView() { ViewInvoker.showView(view.getDialog()); }
    
}
