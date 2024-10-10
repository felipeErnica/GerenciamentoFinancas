package com.santacarolina.dto.nfe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Cobr(
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "dup")
        List<Dup> dupList
) {
}
