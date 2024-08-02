package com.santacarolina.mavenproject1.dto;

public record ProductDTO(prod prod) {
    private record prod(String xProd,
                        String uCom,
                        double qCom,
                        double vUnCom){
    }
}
