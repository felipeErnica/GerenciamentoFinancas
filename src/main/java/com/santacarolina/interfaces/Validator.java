package com.santacarolina.interfaces;

import com.santacarolina.exceptions.FetchFailException;

public interface Validator {
    boolean validate() throws FetchFailException;
}
