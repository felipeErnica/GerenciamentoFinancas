package com.santacarolina.model;

/**
 * ProdutoDuplicata
 */
public class ProdutoDuplicata {

    private Produto produto;
    private Duplicata duplicata;

    public ProdutoDuplicata(Produto produto, Duplicata duplicata) {
        this.produto = produto;
        this.duplicata = duplicata;
    }

    public ProdutoDuplicata() { }

    public Produto getProduto() { return produto; }
    public Duplicata getDuplicata() { return duplicata; }

}
