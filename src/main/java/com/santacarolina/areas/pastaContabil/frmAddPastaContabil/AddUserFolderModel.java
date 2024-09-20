package com.santacarolina.areas.pastaContabil.frmAddPastaContabil;

import com.santacarolina.dao.PastaDao;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.model.beans.PastaContabil;
import com.santacarolina.util.AbstractFormModel;
import com.santacarolina.util.OptionDialog;

public class AddUserFolderModel extends AbstractFormModel {

    public static final String BANCO = "banco";
    public static final String NOME_PASTA = "nomePasta";
    public static final String CAMINHO = "caminho";

    private PastaContabil pastaContabil;
    private String nomePasta;
    private String folderPath;
    private ContaBancaria contaBancaria;

    public AddUserFolderModel(PastaContabil p) {
        this.pastaContabil = p;
    }

    @Override
    public boolean updatingNotAllowed() {
        if (this.nomePasta == null || this.nomePasta.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "Adicione o Nome da Pasta!",
                    "Informação Incompleta!"
            );
            return true;
        } else if (this.folderPath == null || this.folderPath.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "A pastaContabil precisa de um caminho relacionado!",
                    "Informação Incompleta!"
            );
            return true;
        } else if (this.contaBancaria == null) {
            OptionDialog.showErrorDialog(
                    "A pastaContabil precisa de uma conta relacionada!",
                    "Informação Incompleta!"
            );
            return true;
        } else return false;
    }

    public boolean isNomeRepetido() throws FetchFailException {
        var nomeRepetido = new PastaDao().findByNome(this.nomePasta);
        if (nomeRepetido.isPresent()) {
            OptionDialog.showErrorDialog(
                    "Já existe uma pastaContabil com este nome!",
                    "Nome repetido!"
            );
            return true;
        } else return false;
    }

    public PastaContabil getPastaContabil() { return pastaContabil; }
    public String getNomePasta() { return nomePasta; }
    public String getFolderPath() { return folderPath; }
    public ContaBancaria getContaBancaria() { return contaBancaria; }

    public void setNomePasta(String nomePasta) {
        this.nomePasta = nomePasta.toUpperCase();
        pastaContabil.setNome(this.nomePasta);
        fireChange(NOME_PASTA);
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        pastaContabil.setCaminhoPasta(folderPath);
        fireChange(CAMINHO);
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        pastaContabil.setContaBancaria(contaBancaria);
    }

}
