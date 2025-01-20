package com.santacarolina.model.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pag {

    @JacksonXmlProperty(localName = "detPag")
    private DetPag detPag;

    public double getvPag() { return detPag.getVPag(); }
}
