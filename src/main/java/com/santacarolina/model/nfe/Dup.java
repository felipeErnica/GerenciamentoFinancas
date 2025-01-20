package com.santacarolina.model.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dup(
        int nDup,
        String dVenc,
        double vDup
) {
}
