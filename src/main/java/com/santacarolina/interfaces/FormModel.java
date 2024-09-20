package com.santacarolina.interfaces;

public interface FormModel {
    void addListener(FormListener listener);
    void removeListener(FormListener listener);
    void fireChange(String... properties);
}
