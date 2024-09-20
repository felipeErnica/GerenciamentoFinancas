package com.santacarolina.dto.nfe;

public record ProdutoNfeDTO(prod prod) {
    public record prod(String xProd,
                        String uCom,
                        double qCom,
                        double vUnCom){
    }
}
