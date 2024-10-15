package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ide {

    @JacksonXmlProperty(localName = "nNF")
    private long nNF;
    @JacksonXmlProperty(localName = "dhEmi")
    private String dhEmi;

    public long getnNF() { return nNF; }
    public String getDhEmi() { return dhEmi; }

}
