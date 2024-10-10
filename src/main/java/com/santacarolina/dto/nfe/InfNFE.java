package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfNFE {

    @JacksonXmlProperty(localName = "ide")
    private Ide ide;
    @JacksonXmlProperty(localName = "emit")
    private Emit emit;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "det")
    private List<Det> detList;
    @JacksonXmlProperty(localName = "total")
    private Total total;
    private Cobr cobr;
    private Pag pag;

    public Ide getIde() { return ide; }
    public Emit getEmit() { return emit; }
    public List<Det> getDetList() { return detList; }
    public Total getTotal() { return total; }
    public Pag getPag() { return pag; }
    public Cobr getCobr() { return cobr; }

}
