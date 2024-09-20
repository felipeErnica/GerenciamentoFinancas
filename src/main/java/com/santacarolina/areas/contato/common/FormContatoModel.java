package com.santacarolina.areas.contato.common;

import com.santacarolina.interfaces.NewFormModel;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.util.PropertyFirer;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class FormContatoModel implements NewFormModel {

    public static final String NOME = "nome";
    public static final String CPF = "cpf";
    public static final String CNPJ = "cnpj";
    public static final String IE = "ie";
    public static final String DOC_CHECKBOX = "docCheckBox";
    public static final String DOC_ENABLED = "docEnabled";
    public static final String CPF_INVALID = "cpfInvalid";
    public static final String CNPJ_INVALID = "cnpjInvalid";
    public static final String IE_INVALID = "ieInvalid";

    private Contato contato;
    private String name;
    private boolean docsEnabled;
    private String cpf;
    private String cnpj;
    private String ie;
    private boolean ieInvalidFormat;
    private boolean cnpjInvalidFormat;
    private boolean cpfInvalidFormat;
    private PropertyFirer pf;
    private boolean isUpdating;

    public FormContatoModel(Contato contato) {
        this.pf = new PropertyFirer(this);
        this.contato = contato;
        updateAllData();
    }

    private void updateAllData() {
        name = contato.getNome();
        if (!contato.isEmptyDocuments()) {
            cnpj = contato.printCnpj();
            ie = contato.printIe();
            cpf = contato.printCpf();
            docsEnabled = true;
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { pf.addPropertyChangeListener(listener); }

    @Override
    public void fireInitialChanges() {
        isUpdating = true;
        if (this.contato.getId() != 0) {
            pf.firePropertyChange(NOME, name);
            pf.firePropertyChange(DOC_ENABLED, docsEnabled);
            pf.firePropertyChange(DOC_CHECKBOX, docsEnabled);
            pf.firePropertyChange(CPF, cpf);
            pf.firePropertyChange(CNPJ, cnpj);
            pf.firePropertyChange(IE, ie);
            pf.firePropertyChange(CPF_INVALID, cpfInvalidFormat);
            pf.firePropertyChange(CNPJ_INVALID, cnpjInvalidFormat);
            pf.firePropertyChange(IE_INVALID, ieInvalidFormat);
        }
        isUpdating = false;
    }

    public Contato getContato() { return contato; }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public String getIe() { return ie; }
    public boolean isDocsEnabled() { return docsEnabled; }
    public boolean isIeInvalidFormat() { return ieInvalidFormat; }
    public boolean isCnpjInvalidFormat() { return cnpjInvalidFormat; }
    public boolean isCpfInvalidFormat() { return cpfInvalidFormat; }

    public void setName(String name) {
        if (isUpdating) return;
        isUpdating = true;
        this.name = name.toUpperCase();
        contato.setNome(this.name);
        pf.firePropertyChange(NOME, this.name);
        isUpdating = false;
    }

    public void setDocsEnabled(boolean docsEnabled) {
        if (isUpdating) return;
        isUpdating = true;
        this.docsEnabled = docsEnabled;

        if (!docsEnabled) {
            cnpj = "";
            cpf = "";
            ie = "";
            ieInvalidFormat = false;
            cpfInvalidFormat = false;
            cnpjInvalidFormat = false;

            contato.setCpf(cpf);
            contato.setIe(ie);
            contato.setCnpj(cnpj);

            pf.firePropertyChange(CPF, cpf);
            pf.firePropertyChange(CNPJ, cnpj);
            pf.firePropertyChange(IE, ie);
            pf.firePropertyChange(IE_INVALID, ieInvalidFormat);
            pf.firePropertyChange(CNPJ_INVALID, cnpjInvalidFormat);
            pf.firePropertyChange(CPF_INVALID, cpfInvalidFormat);
        }

        pf.firePropertyChange(DOC_ENABLED, docsEnabled);
        isUpdating = false;
    }

    public void setCpf(String cpf) {
        if (isUpdating) return;
        isUpdating = true;
        this.cpf = cpf;
        cpf = cpf.replaceAll("[^\\d]","");
        contato.setCpf(cpf);

        if (contato.isValidCpf()) {
            this.cpf = contato.printCpf();
            cpfInvalidFormat = false;
        } else {
            cpfInvalidFormat = !StringUtils.isBlank(this.cpf);
            contato.setCpf(this.cpf);
        }

        pf.firePropertyChange(CPF, this.cpf);
        pf.firePropertyChange(CPF_INVALID, cpfInvalidFormat);
        isUpdating = false;
    }

    public void setCnpj(String cnpj) {
        if (isUpdating) return;
        isUpdating = true;
        this.cnpj = cnpj;
        cnpj = cnpj.replaceAll("[^\\d]","");
        contato.setCnpj(cnpj);

        if (contato.isValidCnpj()) {
            this.cnpj = contato.printCnpj();
            cnpjInvalidFormat = false;
        } else {
            contato.setCnpj(this.cnpj);
            cnpjInvalidFormat = !StringUtils.isBlank(this.cnpj);
        }

        pf.firePropertyChange(CNPJ, this.cnpj);
        pf.firePropertyChange(CNPJ_INVALID, cnpjInvalidFormat);
        isUpdating = false;
    }

    public void setIe(String ie) {
        if (isUpdating) return;
        isUpdating = true;
        this.ie = ie;
        ie = ie.replaceAll("[^\\d]", "");
        contato.setIe(ie);

        if (contato.isValidIe()) {
            this.ie = contato.printIe();
            ieInvalidFormat = false;
        } else {
            contato.setIe(this.ie);
            ieInvalidFormat = !StringUtils.isBlank(this.ie);
        }

        pf.firePropertyChange(IE, this.ie);
        pf.firePropertyChange(IE_INVALID, ieInvalidFormat);
        isUpdating = false;
    }

}