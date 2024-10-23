package com.santacarolina.areas.documentos.frmDoc;

import com.santacarolina.areas.documentos.frmDoc.docPanel.DocModel;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupModel;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdutoTableModel;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.model.Duplicata;
import com.santacarolina.model.Produto;
import com.santacarolina.util.OptionDialog;
import com.santacarolina.util.ValidatorViolations;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DocValidator {

    public static boolean validate(MainModel model) throws FetchFailException {
        if (!validateDocInfo(model.getDocModel())) return false;
        if (!validateDup(model.getDupModel())) return false;
        if (!validateProd(model.getProdutoModel().getTableModel())) return false;
        DocumentoFiscal doc = model.getDocumentoFiscal();
        if (doc.getId() == 0) {
            DocumentoDAO dao = new DocumentoDAO();
            if (doc.getTipoDoc() != TipoDoc.NOTA_FISCAL && doc.getTipoDoc() != TipoDoc.NFE) {
                if (dao.exists(doc)) {
                    OptionDialog.showErrorDialog("Este documento já foi lançado!", "Documento já existe!");
                    return false;
                }
            } else {
                if (dao.notaExists(doc)) {
                    OptionDialog.showErrorDialog("Esta nota já foi lançada!", "Documento já existe!");
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean validateDocInfo (DocModel model) {
        if (model.getEmissor() == null) {
            ValidatorViolations.violateEmptyFields("Contato");
            return false;
        } else if (model.getTipoDoc() == null) {
            ValidatorViolations.violateEmptyFields("Tipo de Documento");
            return false;
        } else if (model.getPastaContabil() == null) {
            ValidatorViolations.violateEmptyFields("Pasta Contábil");
            return false;
        } else if (model.getFluxoCaixa() == null) {
            ValidatorViolations.violateEmptyFields("Fluxo de Caixa");
            return false;
        } else if (model.isInvalidDate()) {
            ValidatorViolations.violateInvalidFields("Data de Emissão");
            return false;
        } else if (model.getEmissionDate() == null) {
            ValidatorViolations.violateEmptyFields("Data de Emissão");
            return false;
        } else if (model.isInvalidValue()) {
            ValidatorViolations.violateInvalidFields("Valor do Documento");
            return false;
        } else if (model.getDocValue() == null) {
            ValidatorViolations.violateEmptyFields("Valor do Documento");
            return false;
        } else if (model.isDocNumberEnable()) {
            if (model.isInvalidDocNumber()) {
                ValidatorViolations.violateInvalidFields("Número do Documento");
                return false;
            } else if (model.getDocNumber() == null) {
                ValidatorViolations.violateEmptyFields("Número do Documento");
                return false;
            }
        }
        return true;
    }

    private static boolean validateDup(DupModel model) {
        if (model.getTipoPagamento() == null) {
            ValidatorViolations.violateEmptyFields("Tipo de Pagamento");
            return false;
        }
        List<Duplicata> list = model.getTableModel().getDuplicataList();
        if (list.isEmpty()) {
            ValidatorViolations.violateEmptyList("Duplicatas");
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            Duplicata d = list.get(i);
            if (d.getValor() == 0) {
                ValidatorViolations.violateEmptyFieldList("Valor", "Duplicatas", i + 1);
                return false;
            } else if (d.getDataVencimento() == null) {
                ValidatorViolations.violateEmptyFieldList("Data de Vencimento", "Duplicatas", i + 1);
                return false;
            }
        }
        return true;
    }

    private static boolean validateProd(ProdutoTableModel model) {
        List<Produto> list = model.getProdutoList();
        if (list.isEmpty()) {
            ValidatorViolations.violateEmptyList("Produtos");
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            Produto p = list.get(i);
            if (p.getClassificacao() == null) {
                ValidatorViolations.violateEmptyFieldList("Classificação", "Produtos", i + 1);
                return false;
            } else if (StringUtils.isBlank(p.getDescricao())) {
                ValidatorViolations.violateEmptyFieldList("Descrição", "Produtos", i + 1);
                return false;
            } else if (StringUtils.isBlank(p.getUnd())) {
                ValidatorViolations.violateEmptyFieldList("Unidade", "Produtos", i + 1);
                return false;
            } else if (p.getQuantidade() == 0) {
                ValidatorViolations.violateEmptyFieldList("Quantidade", "Produtos", i + 1);
                return false;
            } else if (p.getQuantidade() < 0) {
                ValidatorViolations.violateInvalidFieldList("Quantidade", "Produtos", i + 1);
                return false;
            } else if (p.getValorUnit() == 0) {
                ValidatorViolations.violateEmptyFieldList("Valor Unitário", "Produtos", i + 1) ;
                return false;
            }
        }
        return true;
    }

}
