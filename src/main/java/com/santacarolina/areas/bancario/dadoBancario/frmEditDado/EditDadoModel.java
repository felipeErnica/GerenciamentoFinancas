package com.santacarolina.areas.bancario.dadoBancario.frmEditDado;

import com.santacarolina.enums.TipoPix;
import com.santacarolina.model.beans.Banco;
import com.santacarolina.model.beans.ChavePix;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DadoBancario;
import com.santacarolina.util.AbstractFormModel;

import javax.swing.*;

public class EditDadoModel extends AbstractFormModel {

    public static final String PIX_CHECKBOX = "pixCheckBox";
    public static final String TIPO_PIX = "tipoPix";
    public static final String CHAVE_PIX = "chavePix";
    public static final String CONTATO = "contato";
    public static final String BANCO = "banco";
    public static final String NUM_CONTA = "numConta";
    public static final String AGENCIA = "agencia";

    private DadoBancario dadoBancario;
    private Long pixIdOrginal;
    private ChavePix chavePix;
    private Contato contato;
    private Banco banco;
    private String agencia;
    private String numConta;
    private TipoPix tipoPix;
    private String chave;
    private boolean pixEnabled;
    private boolean pixInvalidFormat;

    public boolean updatingNotAllowed() {
        if (this.contato.getNome().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Adicione um Contato!",
                    "ERRO: Informação Incompleta!",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (this.banco.getNomeBanco().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Adicione um Banco!",
                    "ERRO: Informação Incompleta!",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (this.numConta.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Adicione o Número da Conta!",
                    "ERRO: Informação Incompleta!",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (this.agencia.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Adicione o Número da Agência!",
                    "ERRO: Informação Incompleta!",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        }

        if (pixEnabled) {
            if (isPixInvalidFormat()) {
                JOptionPane.showMessageDialog(null,
                        "Formato de Chave Inválido!",
                        "ERRO: Informação Incompleta!",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            } else if (this.chave.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Adicione uma Chave Pix!",
                        "ERRO: Informação Incompleta!",
                        JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }

        return false;

    }

    public boolean deleteOriginalPix() { return pixIdOrginal != null && !pixEnabled; }
    public Long getPixIdOrginal() { return pixIdOrginal; }
    public DadoBancario getDadoBancario() { return dadoBancario; }
    public ChavePix getChavePix() { return chavePix; }
    public Contato getContato() { return contato; }
    public Banco getBanco() { return banco; }
    public String getAgencia() { return agencia; }
    public String getNumConta() { return numConta; }
    public TipoPix getTipoPix() { return tipoPix; }
    public String getChave() { return chave; }
    public boolean isPixEnabled() { return pixEnabled; }
    public boolean isPixInvalidFormat() { return pixInvalidFormat; }

    public void setDadoBancario(DadoBancario d) {
        this.dadoBancario = d;
        this.pixIdOrginal = d.getPixId();
        this.contato = d.getContato();
        this.banco = d.getBanco();
        this.agencia = d.getAgencia();
        this.numConta = d.getNumeroConta();
        this.chavePix = d.getChavePix();

        if (d.getChavePix() != null ) {
            this.pixEnabled = true;
            this.pixInvalidFormat = d.getChavePix().isInvalidFormat();
            this.tipoPix = this.chavePix.getTipoPix();
            this.chave = this.chavePix.getChave();
        }
        fireChange(PIX_CHECKBOX, CHAVE_PIX, TIPO_PIX, CONTATO, BANCO ,AGENCIA, NUM_CONTA);
    }

    public void setContato(Contato contato) {
        this.contato = contato;
        this.dadoBancario.setContato(contato);
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
        this.dadoBancario.setBanco(banco);
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
        this.dadoBancario.setAgencia(agencia);
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
        this.dadoBancario.setNumeroConta(numConta);
    }

    public void setTipoPix(TipoPix tipoPix) {
        if (!pixEnabled) return;
        this.tipoPix = tipoPix;
        this.chavePix.setTipoPix(tipoPix);
        this.pixInvalidFormat = chavePix.isInvalidFormat();
        fireChange(CHAVE_PIX);
    }

    public void setChave(String chave) {
        if (!pixEnabled) return;
        this.chavePix.setChave(chave);
        this.chave = chavePix.toString();
        pixInvalidFormat = chavePix.isInvalidFormat();
        fireChange(CHAVE_PIX);
    }

    public void setPixEnabled(boolean pixEnabled) {
        this.pixEnabled = pixEnabled;
        if (!pixEnabled) {
            this.dadoBancario.setChavePix(null);
            this.chavePix = null;
            this.chave = "";
            this.tipoPix = null;
        } else {
            this.chavePix = new ChavePix();
            this.chavePix.setContato(this.contato);
            this.dadoBancario.setChavePix(this.chavePix);
        }
        fireChange(CHAVE_PIX, TIPO_PIX);
    }

}
