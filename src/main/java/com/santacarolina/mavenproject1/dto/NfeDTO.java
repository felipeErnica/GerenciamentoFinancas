package com.santacarolina.mavenproject1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(namespace = "http://www.portalfiscal.inf.br/nfe", localName = "nfeProc")
public class NfeDTO {

    @JacksonXmlProperty(namespace = "http://www.portalfiscal.inf.br/nfe", localName = "NFe")
    private NFe NFe;

    public ContactDTO getSender() {return NFe.infNFe.emit;}
    public ContactDTO getReceiver() {return NFe.infNFe.dest;}
    public List<ProductDTO> getProductDTO() {return NFe.infNFe.det;}
    public String getDocNumber() {return NFe.infNFe.ide.nNF;}
    public String getEmissionDate() {return NFe.infNFe.ide.dhEmi;}
    public double getValue() {return NFe.infNFe.total.ICMSTot.vNF;}
    public List<PaymentDTO> getPayemntDTO() {return NFe.infNFe.cobr.dup;}

    private static class NFe {
        @JacksonXmlProperty(localName = "infNFe")
        infNFe infNFe;
    }

    private static class infNFe {
        @JacksonXmlProperty(localName = "ide")
        private ide ide;
        private ContactDTO emit;
        private ContactDTO dest;
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<ProductDTO> det;
        @JacksonXmlProperty(localName = "total")
        private total total;
        @JacksonXmlProperty(localName = "cobr")
        private cobr cobr;
    }

    private static class ide {
        private String nNF;
        private String dhEmi;
    }

    private static class total {
        @JacksonXmlProperty(localName = "ICMSTot")
        private ICMSTot ICMSTot;
    }

    private static class ICMSTot {
        private double vNF;
    }

    private static class cobr {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<PaymentDTO> dup;
    }
}
