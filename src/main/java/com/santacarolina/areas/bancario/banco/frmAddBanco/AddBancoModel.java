package com.santacarolina.areas.bancario.banco.frmAddBanco;

import com.santacarolina.dao.BancoDAO;
import com.santacarolina.enums.Replacement;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Banco;
import com.santacarolina.util.AbstractFormModel;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

import javax.swing.*;

public class AddBancoModel extends AbstractFormModel {

    public static final String NOME_BANCO = "banco";
    public static final String APELIDO  = "apelidoBanco";

    private Banco banco;
    private String apelidoBanco;
    private String nomeBanco;

    public AddBancoModel(Banco banco) {
        this.banco = banco;
    }

    @Override
    public boolean updatingNotAllowed() {
        if (this.nomeBanco == null || this.nomeBanco.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "Adicione o Nome do Banco!",
                    "Informação Incompleta"
            );
            return true;
        } else return false;
    }

    public Replacement replaceBanco() {
        try {
            var optional = new BancoDAO().findByNome(this.nomeBanco);
            if (optional.isPresent()) {
                int result = OptionDialog.showOptionDialog(
                        "Este banco já existe. Deseja substituí-lo por este?",
                        "Banco já Existe!");
                if (result == JOptionPane.YES_OPTION) {
                    Banco banco = optional.get();
                    this.banco.setId(banco.getId());
                    return Replacement.REPLACE_ACCEPTED;
                } else return Replacement.REPLACE_REJECTED;
            } else return Replacement.NO_REPEATING_FOUND;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return Replacement.REPLACE_ACCEPTED;
        }
    }

    public Banco getBanco() { return banco; }
    public String getApelidoBanco() { return apelidoBanco; }
    public String getNomeBanco() { return nomeBanco; }

    public void setApelidoBanco(String apelidoBanco) {
        this.apelidoBanco = apelidoBanco.toUpperCase();
        this.banco.setApelidoBanco(this.apelidoBanco);
        fireChange(APELIDO);
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco.toUpperCase();
        this.banco.setNomeBanco(this.nomeBanco);
        fireChange(NOME_BANCO);
    }

}
