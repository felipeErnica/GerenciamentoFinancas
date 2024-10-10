package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ICMSTot {

    @JacksonXmlProperty(localName = "vNF")
    private double vNF;

    public double getvNF() { return vNF; }
}
