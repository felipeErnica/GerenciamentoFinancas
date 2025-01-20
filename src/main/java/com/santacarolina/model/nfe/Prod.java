package com.santacarolina.model.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Prod {

    @JacksonXmlProperty(localName = "xProd")
    private String xProd;
    @JacksonXmlProperty(localName = "uCom")
    private String uCom;
    @JacksonXmlProperty(localName = "qCom")
    private Double qCom;
    @JacksonXmlProperty(localName = "vUnCom")
    private Double vUnCom;

    public String getxProd() { return xProd; }
    public String getuCom() { return uCom; }
    public Double getqCom() { return qCom; }
    public Double getvUnCom() { return vUnCom; }

}
