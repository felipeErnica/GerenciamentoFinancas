package com.santacarolina.util;

public class ValidatorViolations {

    public static void violateEmptyFields(String field) {
        OptionDialog.showErrorDialog("O campo " + field + " não foi preenchido!",
                "Campo não preenchido!");
    }

    public static void violateInvalidFields(String field) {
        OptionDialog.showErrorDialog( "O formato do campo " + field + " está incorreto!",
                "Formato Incorreto!");
    }

}
