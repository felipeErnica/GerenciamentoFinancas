package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Emit {

    @JacksonXmlProperty(localName = "xFant")
    private String xFant;
    @JacksonXmlProperty(localName = "CNPJ")
    private String cnpj;
    @JacksonXmlProperty(localName = "IE")
    private String ie;
    @JacksonXmlProperty(localName = "cpf")
    private String cpf;

    public String getxFant() { return xFant; }
    public String getCnpj() { return cnpj; }
    public String getIe() { return ie; }
    public String getCpf() { return cpf; }

}
