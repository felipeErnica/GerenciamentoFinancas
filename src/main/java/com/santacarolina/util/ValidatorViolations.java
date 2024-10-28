package com.santacarolina.util;

public class ValidatorViolations {

    public static void violateEmptyFields(String field) {
        OptionDialog.showErrorDialog("O campo " + field + " não foi preenchido!",
                "Campo não preenchido!");
    }

    public static void violateInvalidFields(String field) {
        OptionDialog.showErrorDialog("O formato do campo " + field + " está incorreto!",
                "Formato Incorreto!");
    }

    public static void violateEmptyList(String listName) {
        OptionDialog.showErrorDialog("A lista de " + listName + " esta vazia!", "Lista não preenchida!");
    }

    public static void violateEmptyFieldList(String listField, String listName, int row) {
        OptionDialog.showErrorDialog("O campo " + listField + " na linha " + row + " da lista de " + listName + " está vazio!",
                "Lista não preenchida!");
    }

    public static void violateInvalidFieldList(String listField, String listName, int row) {
        OptionDialog.showErrorDialog("O campo " + listField + " na linha " + row + " da lista de " + listName + " está preenchido incorretamente!",
                "Lista Inválida!");
    }
    
    public static void violateRecordExists(String prompt) {
        OptionDialog.showErrorDialog(prompt, "Registro já existe!");
    }

}
