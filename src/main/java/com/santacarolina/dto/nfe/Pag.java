package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pag {

    private double vPag;

    public double getvPag() { return vPag; }
}
