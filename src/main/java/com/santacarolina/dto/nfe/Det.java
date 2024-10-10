package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Det {

    @JacksonXmlProperty(localName = "prod")
    private Prod prod;

    public Prod getProd() { return prod; }

}
