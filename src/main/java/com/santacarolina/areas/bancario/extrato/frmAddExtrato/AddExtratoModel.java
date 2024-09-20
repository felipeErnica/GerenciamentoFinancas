package com.santacarolina.areas.bancario.extrato.frmAddExtrato;

import com.santacarolina.areas.mainFrame.pgBanco.ExtratoTableModel;
import com.santacarolina.model.beans.ContaBancaria;
import com.santacarolina.model.beans.Extrato;

import java.util.ArrayList;
import java.util.List;

public class AddExtratoModel {

    private ContaBancaria contaBancaria;
    private AddExtratoTableModel tableModel;
    private List<Extrato> list;

    public AddExtratoModel(ContaBancaria contaBancaria) {
        tableModel = new AddExtratoTableModel();
        this.contaBancaria = contaBancaria;
    }

    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public AddExtratoTableModel getTableModel() { return tableModel; }

}
