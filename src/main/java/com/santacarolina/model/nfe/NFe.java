package com.santacarolina.model.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NFe {

    @JacksonXmlProperty(localName = "infNFe")
    private InfNFE infNFe;

    public InfNFE getInfNFe() { return infNFe; }

}
