package com.santacarolina.mavenproject1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(namespace = "http://www.portalfiscal.inf.br/nfe", localName = "nfeProc")
public record NfeDTO (@JacksonXmlProperty(namespace = "http://www.portalfiscal.inf.br/nfe", localName = "NFe") NFe NFe){

    public record NFe(InfNFe infNFe){
    }
    public record InfNFe(
        Ide ide,
        ContactDTO emit,
        ContactDTO dest,
        @JacksonXmlElementWrapper(useWrapping = false)
        List<ProductDTO> det,
        Total total,
        Cobr cobr
    ){
    }
    public record Ide(
        String nNF,
        String dhEmi
    ){
    }
    public record Total (ICMSTot ICMSTot){
    }
    public record ICMSTot (double vNF){
    }
    public record Cobr(
        @JacksonXmlElementWrapper(useWrapping = false)
        List<PaymentDTO> dup
    ){
    }
}
