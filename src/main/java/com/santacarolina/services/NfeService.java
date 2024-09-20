package com.santacarolina.services;

import com.santacarolina.dto.nfe.ContatoNfeDTO;
import com.santacarolina.dto.nfe.DuplicataNfeDTO;
import com.santacarolina.dto.nfe.NfeDTO;
import com.santacarolina.dto.nfe.ProdutoNfeDTO;
import com.santacarolina.enums.FluxoCaixa;
import com.santacarolina.enums.TipoDoc;
import com.santacarolina.model.beans.Contato;
import com.santacarolina.model.beans.DocumentoFiscal;
import com.santacarolina.model.beans.Duplicata;
import com.santacarolina.model.beans.Produto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class NfeService {

    public static DocumentoFiscal returnDoc(NfeDTO nfeDTO){

        LocalDate dataEmissao = convertDate(nfeDTO.NFe().infNFe().ide().dhEmi());

        DocumentoFiscal documentoFiscal = new DocumentoFiscal().builder()
                .setFluxoCaixa(FluxoCaixa.DESPESA)
                .setNumDoc(nfeDTO.NFe().infNFe().ide().nNF())
                .setEmissor(getEmissor(nfeDTO.NFe().infNFe().emit()))
                .setValor(nfeDTO.NFe().infNFe().total().ICMSTot().vNF())
                .setDataEmissao(dataEmissao)
                .setDocType(TipoDoc.NFE)
                .build();

        if (nfeDTO.NFe().infNFe().cobr() != null) {
            List<Duplicata> duplicataList = getDuplicatas(nfeDTO.NFe().infNFe().cobr().dup());
            duplicataList.forEach(documentoFiscal::addDuplicata);
        }

        List<Produto> produtoList = getProdutos(nfeDTO.NFe().infNFe().det());
        produtoList.forEach(documentoFiscal::addProduto);

        return documentoFiscal;

    }

    private static LocalDate convertDate(String dhEmi) {
        final int DATE_SIZE = 10;
        String date = dhEmi.substring(0,DATE_SIZE);
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private static List<Produto> getProdutos(List<ProdutoNfeDTO> produtosNfe){
        return produtosNfe.stream()
                .map(NfeService::getProduto)
                .collect(Collectors.toList());
    }

    private static List<Duplicata> getDuplicatas(List<DuplicataNfeDTO> duplicataNfeList){
        return duplicataNfeList.stream()
                .map(NfeService::getDuplicata)
                .collect(Collectors.toList());
    }

    private static Produto getProduto (ProdutoNfeDTO p) {
        return new Produto.ProdutoBuilder()
                .setDescricao(p.prod().xProd())
                .setUnd(p.prod().uCom())
                .setQuantidade(p.prod().qCom())
                .setValorUnit(Math.abs(p.prod().vUnCom())*-1)
                .build();
    }

    private static Duplicata  getDuplicata (DuplicataNfeDTO d) {
        return new Duplicata().fromNFe(d);
    }

    private static Contato getEmissor (ContatoNfeDTO c){
        return new Contato.ContatoBuilder()
                .setNome(c.xNome())
                .setIe(c.IE())
                .setCnpj(c.CNPJ())
                .setCpf(c.CPF())
                .build();
    }
}
