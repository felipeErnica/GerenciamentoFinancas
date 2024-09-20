package com.santacarolina.areas.pastaContabil.frmAddContaBancaria;

import com.santacarolina.dao.ContaDAO;
import com.santacarolina.enums.Replacement;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.beans.Banco;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.util.AbstractFormModel;
import com.santacarolina.util.CustomErrorThrower;
import com.santacarolina.util.OptionDialog;

import javax.swing.*;

import static com.santacarolina.enums.Replacement.*;

public class AddContaModel extends AbstractFormModel {

    private ContaBancaria contaBancaria;
    private Banco banco;
    private String agencia;
    private String numeroConta;
    private String apelidoConta;

    public AddContaModel(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    @Override
    public boolean updatingNotAllowed() {
        if (this.banco == null){
            OptionDialog.showErrorDialog(
                    "Adicione um Banco!",
                    "Informação Incompleta!"
            );
            return true;
        } else if (this.agencia == null || this.agencia.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "Adicione a Agência Bancária!",
                    "Informação Incompleta!"
            );
            return true;
        } else if (this.numeroConta == null || this.numeroConta.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "Adicione o Número da Conta!",
                    "Informação Incompleta!"
            );
            return true;
        } else if (this.apelidoConta == null || this.apelidoConta.isEmpty()) {
            OptionDialog.showErrorDialog(
                    "Adicione um Apelido para a Conta!",
                    "Informação Incompleta!"
            );
            return true;
        } else return false;
    }

    public Replacement replaceContaRepetida() {
        try {
            var optional = new ContaDAO().findEqual(this.contaBancaria);
            if (optional.isPresent()) {
                int result = OptionDialog.showOptionDialog(
                        "Esta conta já existe. Deseja substituí-la por esta?",
                        "Conta já existe!");
                if (result == JOptionPane.YES_OPTION) {
                    ContaBancaria contaBancaria = optional.get();
                    this.contaBancaria.setId(contaBancaria.getId());
                    return REPLACE_ACCEPTED;
                } else return REPLACE_REJECTED;
            } else return NO_REPEATING_FOUND;
        } catch (FetchFailException e) {
            CustomErrorThrower.throwError(e);
            return REPLACE_REJECTED;
        }
    }

    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public Banco getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public String getApelidoConta() { return apelidoConta; }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.contaBancaria.setBanco(banco);
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        this.contaBancaria.setAgencia(agencia);
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
        this.contaBancaria.setNumeroConta(numeroConta);
    }

    public void setApelidoConta(String apelidoConta) {
        this.apelidoConta = apelidoConta.toUpperCase();
        this.contaBancaria.setNomeConta(this.apelidoConta);
        fireChange();
    }

}
