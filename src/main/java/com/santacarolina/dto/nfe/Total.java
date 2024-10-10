package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Total {

    @JacksonXmlProperty(localName = "ICMSTot")
    private ICMSTot icmsTot;

    public ICMSTot getIcmsTot() { return icmsTot; }

}
