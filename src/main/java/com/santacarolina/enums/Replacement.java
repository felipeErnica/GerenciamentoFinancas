package com.santacarolina.enums;

public enum Replacement {

    NO_REPEATING_FOUND(0),
    REPLACE_REJECTED(1),
    REPLACE_ACCEPTED(2);

    private int replacement;

    Replacement(int replacement) { this.replacement = replacement; }
    public int getReplacement() { return replacement; }
}
