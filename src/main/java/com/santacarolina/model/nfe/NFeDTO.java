package com.santacarolina.model.nfe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.santacarolina.dao.ContatoDAO;
import com.santacarolina.dto.DuplicataDTO;
import com.santacarolina.dto.ProdutoDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.exceptions.FetchFailException;
import com.santacarolina.exceptions.SaveFailException;
import com.santacarolina.model.Contato;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(namespace = "http://www.portalfiscal.inf.br/nfe", localName = "nfeProc")
public class NFeDTO {

    @JsonIgnoreProperties
    private final ContatoDAO contatoDAO = new ContatoDAO();

    @JacksonXmlProperty(localName = "NFe")
    private NFe nFe;

    public NFe getNFe() { return nFe; }
    public Long getNumDoc() { return nFe.getInfNFe().getIde().getnNF(); }
    public double getValor() { return nFe.getInfNFe().getTotal().getIcmsTot().getvNF()*-1; }
    public LocalDate getDataEmissao() {
        String date = nFe.getInfNFe().getIde().getDhEmi().substring(0,10);
        return LocalDate.parse(date);
    }
    public TipoDoc getTipoDoc() { return TipoDoc.NFE; }
    public FluxoCaixa getFluxoCaixa() { return FluxoCaixa.DESPESA; }

    public List<ProdutoDTO> getProdutos() {
        List<ProdutoDTO> produtos = new ArrayList<>();
        List<Det> detList = nFe.getInfNFe().getDetList();
        for (Det det : detList) {
            ProdutoDTO prod = new ProdutoDTO();
            prod.setDescricao(det.getProd().getxProd());
            prod.setQuantidade(det.getProd().getqCom());
            prod.setUnd(det.getProd().getuCom());
            prod.setValorUnit(det.getProd().getvUnCom());
            produtos.add(prod);
        }
        return produtos;
    }

    public List<DuplicataDTO> getDuplicatas() {
        List<DuplicataDTO> duplicataList = new ArrayList<>();

        if (nFe.getInfNFe().getCobr() != null && nFe.getInfNFe().getCobr().dupList() != null) {
            List<Dup> dupList = nFe.getInfNFe().getCobr().dupList();
            for (Dup dup : dupList) {
                DuplicataDTO duplicata = new DuplicataDTO();
                duplicata.setDataVencimento(LocalDate.parse(dup.dVenc()));
                duplicata.setValor(dup.vDup());
                duplicataList.add(duplicata);
            }
        } else {
            DuplicataDTO duplicata = new DuplicataDTO();
            System.out.println(nFe.getInfNFe().getPag().getvPag());
            duplicata.setValor(nFe.getInfNFe().getPag().getvPag());
            duplicata.setDataVencimento(getDataEmissao());
            duplicataList.add(duplicata);
        }
        return duplicataList;
    }

    public Contato getEmissor() throws FetchFailException, SaveFailException {
        String cnpj = nFe.getInfNFe().getEmit().getCnpj();
        String cpf = nFe.getInfNFe().getEmit().getCpf();
        String ie = nFe.getInfNFe().getEmit().getIe();
        Optional<Contato> optionalContato = contatoDAO.findByCnpj(cnpj);
        if (optionalContato.isEmpty()) {
            optionalContato = contatoDAO.findByIe(ie);
            if (optionalContato.isEmpty()) optionalContato = contatoDAO.findByCpf(cpf);
        }

        if (optionalContato.isPresent()) {
            boolean updateContato = false;
            Contato emissor = optionalContato.get();
            if (cnpj != null && !cnpj.equals(emissor.getCnpj())) {
                updateContato = true;
                emissor.setCnpj(cnpj);
            }
            if (ie != null && !ie.equals(emissor.getIe())) {
                updateContato = true;
                emissor.setIe(ie);
            }
            if (cpf != null && cpf.equals(emissor.getCpf())) {
                updateContato = true;
                emissor.setCpf(cpf);
            }
            if (updateContato) contatoDAO.save(emissor);
            return emissor;
        } else {
            Contato contato = new Contato();
            contato.setNome(nFe.getInfNFe().getEmit().getxFant());
            contato.setCnpj(nFe.getInfNFe().getEmit().getCnpj());
            contato.setCpf(nFe.getInfNFe().getEmit().getCpf());
            contato.setIe(nFe.getInfNFe().getEmit().getIe());
            return contato;
        }

    }

}
