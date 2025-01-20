package com.santacarolina.model.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetPag {

    @JacksonXmlProperty(localName = "vPag")
    private double vPag;

    public double getVPag() { return vPag; }

}
