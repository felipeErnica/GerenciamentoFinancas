package com.santacarolina.areas.documentos.frmDoc;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.santacarolina.areas.documentos.frmDoc.docPanel.DocModel;
import com.santacarolina.areas.documentos.frmDoc.dupPanel.DupModel;
import com.santacarolina.areas.documentos.frmDoc.prodPanel.ProdutoTableModel;
import com.santacarolina.dao.DocumentoDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.model.DocumentoFiscal;
import com.santacarolina.util.ValidatorViolations;

public class DocValidator {

    public static boolean validate(MainModel model) throws FetchFailException {
        if (!validateDocInfo(model.getDocModel())) return false;
        if (!validateDup(model.getDupModel())) return false;
        if (!validateProd(model.getProdutoModel().getTableModel())) return false;

        if (docExists(model)) return false;

        return true;
    }

    private static boolean docExists(MainModel model) throws FetchFailException {
        DocumentoFiscal novoDoc = model.getDocumentoFiscal();
        Optional<DocumentoFiscal> optional = novoDoc.getTipoDoc() == TipoDoc.NFE || novoDoc.getTipoDoc() == TipoDoc.NOTA_FISCAL ?
            new DocumentoDAO().findEqualNota(novoDoc) :
            new DocumentoDAO().findEqualDoc(novoDoc);

        if (optional.isPresent()) {
            DocumentoFiscal docFound = optional.get();
            if (docFound.getId() != novoDoc.getId()) {
                ValidatorViolations.violateRecordExists("Este documento já existe!");
                return true;
            }
            return false;
        }
        
        return false;

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
        List<DuplicataDTO> list = model.getTableModel().getDuplicataList();
        if (list.isEmpty()) {
            ValidatorViolations.violateEmptyList("Duplicatas");
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            DuplicataDTO d = list.get(i);
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
        List<ProdutoDTO> list = model.getProdutoList();
        if (list.isEmpty()) {
            ValidatorViolations.violateEmptyList("Produtos");
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            ProdutoDTO p = list.get(i);
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
