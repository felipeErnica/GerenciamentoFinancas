package com.santacarolina.areas.bancario.contaBancaria.frmContaBancaria;

import java.beans.PropertyChangeListener;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.interfaces.ViewUpdater;
import com.santacarolina.model.Banco;
import com.santacarolina.model.ContaBancaria;
import com.santacarolina.util.PropertyFirer;

public class FormModel implements ViewUpdater {

    public static final String APELIDO = "contaBancaria";
    public static final String ABREVIACAO = "abreviacao";
    public static final String BANCO = "banco";
    public static final String AGENCIA = "agencia";
    public static final String NUMERO = "numeroConta";

    private ContaBancaria contaBancaria;
    private Banco banco;
    private String agencia;
    private String numeroConta;
    private String apelidoConta;
    private String abreviacao;

    private PropertyFirer pf;
    private boolean isUpdating;

    public FormModel(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
        pf = new PropertyFirer(this);
        updateAllData();
    }

    private void updateAllData() {
        banco = contaBancaria.getBanco();
        agencia = contaBancaria.getAgencia();
        numeroConta = contaBancaria.getNumeroConta();
        apelidoConta = contaBancaria.getNomeConta();
        abreviacao = contaBancaria.getAbreviacaoConta();
    }

    public ContaBancaria getContaBancaria() { return contaBancaria; }
    public Banco getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getNumeroConta() { return numeroConta; }
    public String getApelidoConta() { return apelidoConta; }
    public String getAbreviacao() { return abreviacao; }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.contaBancaria.setBanco(banco);
        triggerAbreviacao();
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        this.contaBancaria.setAgencia(agencia);
        triggerAbreviacao();
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
        this.contaBancaria.setNumeroConta(numeroConta);
        triggerAbreviacao();
    }

    public void setApelidoConta(String apelidoConta) {
        if (isUpdating) return;
        this.apelidoConta = apelidoConta.toUpperCase();
        this.contaBancaria.setNomeConta(this.apelidoConta);
        isUpdating = true;
        pf.firePropertyChange(APELIDO, this.apelidoConta);
        isUpdating = false;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
        contaBancaria.setAbreviacao(abreviacao);
    }

    private void triggerAbreviacao() {
        if (isUpdating) return;
        if (!StringUtils.isBlank(abreviacao)) return;
        if (banco != null && !StringUtils.isBlank(agencia) && !StringUtils.isBlank(numeroConta)) {
            StringBuilder abreviacaoBuilder = new StringBuilder();
            if (banco.getApelidoBanco() != null) abreviacaoBuilder.append(banco.getApelidoBanco());
            abreviacaoBuilder.append(" AG: ").append(agencia);
            abreviacaoBuilder.append(" CC: ").append(numeroConta);
            isUpdating = true;
            pf.firePropertyChange(ABREVIACAO, abreviacaoBuilder.toString());
            isUpdating = false;
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        pf.firePropertyChange(BANCO, banco);
        pf.firePropertyChange(AGENCIA, agencia);
        pf.firePropertyChange(NUMERO, numeroConta);
        pf.firePropertyChange(APELIDO, apelidoConta);
        pf.firePropertyChange(ABREVIACAO, abreviacao);
    }

}
